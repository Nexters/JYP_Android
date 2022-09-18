package com.jyp.core.search_place.domain.model


data class SearchPlaceResponseModel(
    val meta: PlaceMeta,                    // 장소 메타데이터
    val documents: List<Place>              // 검색 결과
) {
    data class PlaceMeta(
        val total_count: Int,               // 검색어에 검색된 문서 수
        val pageable_count: Int,            // total_count 중 노출 가능 문서 수, 최대 45 (API에서 최대 45개 정보만 제공)
        val is_end: Boolean,                // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
        val same_name: RegionInfo           // 질의어의 지역 및 키워드 분석 정보
    ) {
        data class RegionInfo(
            val region: List<String>,       // 질의어에서 인식된 지역의 리스트, ex) '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트
            val keyword: String,            // 질의어에서 지역 정보를 제외한 키워드, ex) '중앙로 맛집' 에서 '맛집'
            val selected_region: String     // 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
        )
    }

    data class Place(
        val id: String,                     // 장소 ID
        val place_name: String,             // 장소명, 업체명
        val category_name: String,          // 카테고리 이름
        val category_group_code: String,    // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
        val category_group_name: String,    // 중요 카테고리만 그룹핑한 카테고리 그룹명
        val phone: String,                  // 전화번호
        val address_name: String,           // 전체 지번 주소
        val road_address_name: String,      // 전체 도로명 주소
        val x: String,                      // X 좌표값 혹은 longitude
        val y: String,                      // Y 좌표값 혹은 latitude
        val place_url: String,              // 장소 상세페이지 URL
        val distanc: String                 // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter
    )
}
