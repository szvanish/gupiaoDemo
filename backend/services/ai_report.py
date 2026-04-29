import json
from openai import OpenAI
from datetime import datetime
from models.stock import StockAnalysis, AIReport

SYSTEM_PROMPT = """你是一位资深券商首席分析师，拥有20年A股、港股、美股研究经验。
请根据提供的股票六维量化数据，用专业的研报风格撰写完整的投资分析报告。
报告必须客观、专业、有据可依，风险提示要真实充分，不得做出无根据的极端预测。
输出必须是严格的 JSON 格式，字段完全按照用户指定的结构输出，不添加任何额外说明。"""

def build_prompt(analysis: StockAnalysis) -> str:
    return f"""请对 {analysis.name}（{analysis.code}，{analysis.market}股）进行完整投资分析。

=== 六维量化数据 ===

【技术面评分: {analysis.technical.score}/100】
- MA5={analysis.technical.ma5}, MA20={analysis.technical.ma20}, MA60={analysis.technical.ma60}
- MACD: DIF={analysis.technical.macd_dif}, DEA={analysis.technical.macd_dea}, 柱={analysis.technical.macd_bar}
- RSI6={analysis.technical.rsi6}, RSI12={analysis.technical.rsi12}
- KDJ: K={analysis.technical.kdj_k}, D={analysis.technical.kdj_d}, J={analysis.technical.kdj_j}
- 布林带: 上={analysis.technical.boll_upper}, 中={analysis.technical.boll_mid}, 下={analysis.technical.boll_lower}
- 量比={analysis.technical.volume_ratio}, 换手率={analysis.technical.turnover_rate}

【基本面评分: {analysis.fundamental.score}/100】
- PE(TTM)={analysis.fundamental.pe_ttm}, PB={analysis.fundamental.pb}, ROE={analysis.fundamental.roe}%
- 营收同比增速={analysis.fundamental.revenue_growth_yoy}%, 净利润同比增速={analysis.fundamental.profit_growth_yoy}%
- 毛利率={analysis.fundamental.gross_margin}%, 净利率={analysis.fundamental.net_margin}%
- 资产负债率={analysis.fundamental.debt_ratio}%, EPS={analysis.fundamental.eps}
- 股息率={analysis.fundamental.dividend_yield}%

【估值面评分: {analysis.valuation.score}/100】
- 行业平均PE={analysis.valuation.industry_avg_pe}, 行业平均PB={analysis.valuation.industry_avg_pb}
- 历史PE分位数={analysis.valuation.pe_percentile}%, PEG={analysis.valuation.peg}

【情绪面评分: {analysis.sentiment.score}/100】
- 主力净流入={analysis.sentiment.main_net_inflow}元
- 超大单净额={analysis.sentiment.super_large_net}元, 大单净额={analysis.sentiment.large_net}元
- 北向资金净买入={analysis.sentiment.northbound_net}元
- 融资余额变化={analysis.sentiment.margin_balance}, 新闻热度={analysis.sentiment.news_heat_score}/100

【筹码与机构评分: {analysis.chip.score}/100】
- 机构持仓占比={analysis.chip.institution_holding_pct}%
- 股东人数变化={analysis.chip.shareholder_count_change_pct}%
- 主要股东变化: {analysis.chip.top10_holder_change}

【宏观与行业评分: {analysis.macro.score}/100】
- 所属行业: {analysis.macro.industry_name}
- 行业近期涨跌: {analysis.macro.industry_change_pct}%
- 与大盘相关系数: {analysis.macro.corr_with_index}
- 政策摘要: {analysis.macro.policy_summary}

=== 输出要求 ===
请严格输出以下 JSON 结构（不要添加 markdown 代码块标记）：
{{
  "summary": "（100字内核心结论）",
  "market_review": "（近期行情复盘，2-3句）",
  "fundamental_analysis": "（基本面深度解读，3-5句）",
  "technical_analysis": "（技术形态分析，3-5句）",
  "valuation_analysis": "（估值合理性评估，2-3句）",
  "sentiment_analysis": "（资金情绪分析，2-3句）",
  "risks": ["风险1", "风险2", "风险3（至少3条）"],
  "recommendation": "（操作建议，包含买卖时机参考，3-5句）",
  "rating": "（从以下选一：强烈买入/买入/持有/卖出/强烈卖出）",
  "target_price_low": 数字或null,
  "target_price_high": 数字或null
}}"""

class AIReportService:
    def __init__(self, api_key: str):
        self.client = OpenAI(
            api_key=api_key,
            base_url="https://api.deepseek.com",
        )

    def generate(self, analysis: StockAnalysis) -> AIReport:
        response = self.client.chat.completions.create(
            model="deepseek-chat",
            max_tokens=2048,
            messages=[
                {"role": "system", "content": SYSTEM_PROMPT},
                {"role": "user", "content": build_prompt(analysis)},
            ],
        )
        raw = response.choices[0].message.content.strip()
        # Strip markdown code fences if model wraps output
        if raw.startswith("```"):
            raw = raw.split("\n", 1)[1].rsplit("```", 1)[0].strip()
        data = json.loads(raw)
        return AIReport(
            code=analysis.code,
            name=analysis.name,
            market=analysis.market,
            generated_at=datetime.now().isoformat(),
            **data,
        )
