import pytest
import json
from unittest.mock import patch, MagicMock
from services.ai_report import AIReportService, build_prompt
from models.stock import (StockAnalysis, TechnicalIndicators, FundamentalData,
                           ValuationData, SentimentData, ChipData, MacroData, AIReport)

def make_analysis():
    return StockAnalysis(
        code="600036", name="招商银行", market="A",
        technical=TechnicalIndicators(ma5=42.0, ma20=40.0, rsi6=55.0, score=65),
        fundamental=FundamentalData(pe_ttm=7.5, pb=1.0, roe=16.0, score=75),
        valuation=ValuationData(industry_avg_pe=10.0, peg=0.8, score=70),
        sentiment=SentimentData(main_net_inflow=500_000_000, score=75),
        chip=ChipData(institution_holding_pct=45.0, score=65),
        macro=MacroData(industry_name="银行", industry_change_pct=1.5, score=60),
    )

def test_build_prompt_contains_stock_name():
    analysis = make_analysis()
    prompt = build_prompt(analysis)
    assert "招商银行" in prompt
    assert "600036" in prompt

def test_build_prompt_contains_all_dimensions():
    analysis = make_analysis()
    prompt = build_prompt(analysis)
    assert "技术面" in prompt
    assert "基本面" in prompt
    assert "估值" in prompt
    assert "情绪" in prompt

@patch("services.ai_report.anthropic.Anthropic")
def test_generate_report_returns_ai_report(mock_client_cls):
    mock_client = MagicMock()
    mock_client_cls.return_value = mock_client
    fake_response_text = json.dumps({
        "summary": "招商银行基本面优秀，当前估值合理，建议买入。",
        "market_review": "近期股价企稳回升。",
        "fundamental_analysis": "ROE持续保持在16%以上。",
        "technical_analysis": "MACD金叉，RSI处于健康区间。",
        "valuation_analysis": "PEG<1，低于行业平均PE。",
        "sentiment_analysis": "主力净流入5亿，资金积极。",
        "risks": ["宏观经济下行风险", "息差收窄压力", "资产质量风险"],
        "recommendation": "建议以当前价位分批买入，目标价45-48元。",
        "rating": "买入",
        "target_price_low": 45.0,
        "target_price_high": 48.0,
    })
    mock_client.messages.create.return_value = MagicMock(
        content=[MagicMock(text=fake_response_text)]
    )
    svc = AIReportService(api_key="test_key")
    analysis = make_analysis()
    result = svc.generate(analysis)
    assert isinstance(result, AIReport)
    assert result.rating == "买入"
    assert len(result.risks) == 3
    assert result.code == "600036"
