import pytest
from services.fundamental import compute_valuation, score_fundamental
from models.stock import FundamentalData, ValuationData

def test_peg_calculated_correctly():
    fd = FundamentalData(pe_ttm=20.0, revenue_growth_yoy=25.0, score=60)
    result = compute_valuation(fd, industry_avg_pe=25.0, industry_avg_pb=3.0)
    assert isinstance(result, ValuationData)
    assert result.peg is not None
    assert abs(result.peg - (20.0 / 25.0)) < 0.01

def test_score_increases_with_good_metrics():
    fd = FundamentalData(pe_ttm=15.0, pb=1.5, roe=20.0, revenue_growth_yoy=15.0,
                          gross_margin=45.0, debt_ratio=30.0, score=50)
    score = score_fundamental(fd)
    assert score > 60

def test_score_decreases_with_bad_metrics():
    fd = FundamentalData(pe_ttm=100.0, pb=10.0, roe=2.0, revenue_growth_yoy=-10.0,
                          gross_margin=5.0, debt_ratio=80.0, score=50)
    score = score_fundamental(fd)
    assert score < 40

def test_valuation_score_below_industry_avg_is_positive():
    fd = FundamentalData(pe_ttm=15.0, score=50)
    result = compute_valuation(fd, industry_avg_pe=30.0, industry_avg_pb=4.0)
    assert result.score > 50
