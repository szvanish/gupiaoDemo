from models.stock import FundamentalData, ValuationData

def score_fundamental(fd: FundamentalData) -> int:
    score = 50
    if fd.pe_ttm:
        if 0 < fd.pe_ttm < 15:
            score += 15
        elif 15 <= fd.pe_ttm < 30:
            score += 5
        elif fd.pe_ttm > 60:
            score -= 15
    if fd.roe:
        if fd.roe > 20:
            score += 15
        elif fd.roe > 10:
            score += 5
        elif fd.roe < 5:
            score -= 10
    if fd.revenue_growth_yoy:
        if fd.revenue_growth_yoy > 20:
            score += 10
        elif fd.revenue_growth_yoy < 0:
            score -= 10
    if fd.gross_margin:
        if fd.gross_margin > 40:
            score += 5
        elif fd.gross_margin < 10:
            score -= 5
    if fd.debt_ratio:
        if fd.debt_ratio > 70:
            score -= 10
        elif fd.debt_ratio < 40:
            score += 5
    return max(0, min(100, score))

def compute_valuation(
    fd: FundamentalData,
    industry_avg_pe: float | None,
    industry_avg_pb: float | None,
    pe_percentile: float | None = None,
    pb_percentile: float | None = None,
) -> ValuationData:
    peg = None
    if fd.pe_ttm and fd.revenue_growth_yoy and fd.revenue_growth_yoy > 0:
        peg = round(fd.pe_ttm / fd.revenue_growth_yoy, 2)

    score = 50
    if industry_avg_pe and fd.pe_ttm:
        ratio = fd.pe_ttm / industry_avg_pe
        if ratio < 0.7:
            score += 20
        elif ratio < 1.0:
            score += 10
        elif ratio > 1.5:
            score -= 15

    if peg:
        if peg < 1.0:
            score += 10
        elif peg > 2.0:
            score -= 10

    if pe_percentile is not None:
        if pe_percentile < 30:
            score += 10
        elif pe_percentile > 70:
            score -= 10

    return ValuationData(
        industry_avg_pe=industry_avg_pe,
        industry_avg_pb=industry_avg_pb,
        pe_percentile=pe_percentile,
        pb_percentile=pb_percentile,
        peg=peg,
        score=max(0, min(100, score)),
    )
