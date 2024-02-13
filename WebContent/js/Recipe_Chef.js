$(function() {
	// 쉐프 정렬기준 눌렀을 때 속성값이랑 데이터 이동해서 링크로 쏴보자
	{
		let search_word = $("#search_word").val(); // 쉐프 페이지의 검색어를 가져오기
		
		$("#sort_menu1 > li").on("click", function() {
			let sort1 = $(this).attr("sort");
			let sort2 = $(this).parent().parent().parent().find("div:last-child").find("#sort_menu2").attr("sort2");
			
			if(search_word=="undefined" || search_word=="null") {
				// 검색어가 없을 때
				if(sort2=="undefined" || sort2=="null") {
					location.href = "Controller?command=chef_view&sort1=" + sort1;
				} else {
					location.href = "Controller?command=chef_view&sort1=" + sort1 + "&sort2=" + sort2;
				}
			} else {
				// 검색어가 있을 때
				if(sort2=="undefined" || sort2=="null") {
					location.href = "Controller?command=chef_view&sort1=" + sort1 + "&searchWord=" + search_word;
				} else {
					location.href = "Controller?command=chef_view&sort1=" + sort1 + "&sort2=" + sort2 + "&searchWord=" + search_word;
				}
			}
		});
		$("#sort_menu2 > li").on("click", function() {
			let sort1 = $(this).parent().parent().parent().find("div:first-child").find("#sort_menu1").attr("sort1");
			let sort2 = $(this).attr("sort");
			
			if(search_word=="undefined" || search_word=="null") {
				// 검색어가 없을 때
				if(sort1=="undefined" || sort1=="null") {
					location.href = "Controller?command=chef_view&sort2=" + sort2;
				} else {
					location.href = "Controller?command=chef_view&sort1=" + sort1 + "&sort2=" + sort2;
				}
			} else {
				// 검색어가 있을 때
				if(sort1=="undefined" || sort1=="null") {
					location.href = "Controller?command=chef_view&sort2=" + sort2 + "&searchWord=" + search_word;
				} else {
					location.href = "Controller?command=chef_view&sort1=" + sort1 + "&sort2=" + sort2 + "&searchWord=" + search_word;
				}
			}
		});
	}
	
	// 쉐프 페이지에서 소식받기순위쪽 정렬버튼 눌렀을 때 정렬방식이 등장하는 기능
	{
		let sort1 = ".div_chef_sort2_how > div:nth-child(1)";
		let sort2 = ".div_chef_sort2_how > div:nth-child(2)";
		let sort1_ul = sort1 + " > .ul_sort_menu";
		let sort2_ul = sort2 + " > .ul_sort_menu";
		
		$(sort1).on("click", function(event) {
			$(sort1_ul).toggle();
			event.stopPropagation();
		})
		$(sort2).on("click", function(event) {
			$(sort2_ul).toggle();
			event.stopPropagation();
		})
	}
	
	// 정렬방식을 연 상태로 밖을 클릭했을 때 정렬방식창이 닫힘
	{
		let sort1 = ".div_chef_sort2_how > div:nth-child(1)";
		let sort2 = ".div_chef_sort2_how > div:nth-child(2)";
		let sort1_ul = sort1 + " > .ul_sort_menu";
		let sort2_ul = sort2 + " > .ul_sort_menu";
		
		$("body").on("click", function(event) {
			if($(sort1_ul).css("display") != "none") {
				$(sort1_ul).css("display", "none");
				event.stopPropagation();
			}
			if($(sort2_ul).css("display") != "none") {
				$(sort2_ul).css("display", "none");
				event.stopPropagation();
			}
		})
	}
})