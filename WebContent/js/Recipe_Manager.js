		/*그리드 객체 생성 */
		var UserGrid = new ax5.ui.grid();
		var RecipePostGrid = new ax5.ui.grid();
		var MagazineGrid = new ax5.ui.grid();
		var SearchTermsGrid = new ax5.ui.grid();
		var CSGrid = new ax5.ui.grid();

		var UserList = [];
		var CSList = [];
		var RecipePostList = [];
		var SearchTermsList = [];
		var MagazineList = [];

		/* dash(-)로 구분되는 날짜 포맷터 */
		$(document).ready(function () {
			ax5.ui.grid.formatter["date"] = function() {
				var date = this.value;
				if(date.length == 8) {
					return date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6);
				} else {
					return date;
				}
			}
	
			/*그리드 설정 지정 */
			UserGrid.setConfig({
				target: $('[data-ax5grid="UserGrid"]'),
				showRowSelector: true,
				showLineNumber: false,
				multipleSelect: true,
				virtualScrollY: false,
				lineNumberColumnWidth: 40,
				rowSelectorColumnWidth: 27,
				columns: [
					{key: "u_id", label: "회원 ID", align: "center", width:100},
					{key: "u_name", label: "이름", align: "center", width:100},
					{key: "nickname", label: "닉네임", align: "center", width:150},
					{key: "b_date", label: "생년월일", formatter: "date", align: "center"},
					{key: "reg_date", label: "회원가입 날짜", formatter: "date", align: "center"},
					{key: "phone", label: "전화번호", align: "center"},
					{key: "email", label: "이메일", align: "center", width:250},
					{key: "seller", label: "판매자 여부", align: "center",
						editor: { 
							type: "select",
							config: {
								columnKeys: {optionValue: "CD", optionText: "NM"},//추후에 변경
								options: [{CD: "normal", NM: "일반회원"}, {CD: "Seller", NM: "판매자"}],
								},
								disabled: function () {
							        // 활성화 여부를 item.complete 의 값으로 런타임 판단.
							        return this.item.complete == "false";
						      	}
							}	
						},
					{key: "manager", label: "매니저 여부", align: "center",
						editor: {type: "select",
								config: {
									columnKeys: {optionValue: "CD", optionText: "NM"},//추후에 변경
									options: [{CD: "normal", NM: "일반회원"}, {CD: "Manager", NM: "매니저"}]
								},
								disabled: function () {
							        // 활성화 여부를 item.complete 의 값으로 런타임 판단.
							        return this.item.complete == "false";
						      	}
							}
					}
				],
				
				header: {
					selector: false
				},
				body: {
					onClick: function () {
						console.log(this);
					},
					onDataChanged: function () {
						console.log(this);
						let u_id = this.item.u_id;
						let u_name = this.item.u_name;
					}
				}
			});
			
			RecipePostGrid.setConfig({
				target: $('[data-ax5grid="RecipePostGrid"]'),
				showRowSelector: true,
				showLineNumber: false,
				multipleSelect: true,
				virtualScrollY: false,
				lineNumberColumnWidth: 40,
				rowSelectorColumnWidth: 27,
				columns: [
					{key: "u_id", label: "작성자", align: "center"},
					{key: "title", label: "제목", align: "center"},
					{key: "what", label: "종류", align: "center"},//카테고리1
					{key: "situation", label: "상황", align: "center"},//카테고리2
					{key: "kind", label: "유형", align: "center"},//카테고리3
					{key: "writedate", label: "등록 날짜", formatter: "date", align: "center"},//매거진 여부 밑 종류
					{key: "magazine", label: "매거진", align: "center",
						editor: {
							type: "select", config: {
								columnKeys: {
									optionText: "CD",
									optionValue: "TF"
								},
								options: [
									{ CD: "notMagazine", TF: "메거진아님" },
									{ CD: "knowhow", TF: "노하우" },
									{ CD: "convenienceCombo", TF: "편의점" }
								]
							},
							disabled: function () {
								return this.item.complete == "true";
							}
						}
					}
				],
				header: {
					selector: false
				},
				body: {
					onClick: function () {
						console.log(this);
					},
					onDataChanged: function () {
						console.log(this);
					}
				}
			}); 
			
			CSGrid.setConfig({
				target: $('[data-ax5grid="CSGrid"]'),
				showRowSelector: true,
				showLineNumber: false,
				multipleSelect: true,
				virtualScrollY: false,
				lineNumberColumnWidth: 40,
				rowSelectorColumnWidth: 27,
				columns: [
					{key: "u_id", label: "작성자 아이디", align: "center"},
					{key: "nickname", label: "작성자 닉네임", align: "center"},
					{key: "title", label: "제목", align: "center"},
					{key: "content", label: "문의내용", align: "center"},
					{key: "writedate", label: "작성일", formatter: "date", align: "center"}//매거진 여부 밑 종류
				],
				header: {
					selector: false
				},
				body: {
					onClick: function () {
						console.log(this);
					},
					onDataChanged: function () {
						console.log(this);
					}
				}
			}); 
			SearchTermsGrid.setConfig({
				target: $('[data-ax5grid="SearchTermsGrid"]'),
				showRowSelector: true,
				showLineNumber: false,
				multipleSelect: true,
				virtualScrollY: false,
				lineNumberColumnWidth: 40,
				rowSelectorColumnWidth: 27,
				columns: [
					{key: "what", label: "종류", align: "center"},
					{key: "situation", label: "상황", align: "center"},
					{key: "kind", label: "유형", align: "center"},
					{key: "name", label: "이름", align: "center"},
					{key: "image", label: "이미지", align: "center"}
				],
				header: {
					selector: false
				},
				body: {
					mergeCells: ["what", "situation", "kind"],
					onClick: function () {
						console.log(this);
					},
					onDataChanged: function () {
						console.log(this);
					}
				}
			}); 
			MagazineGrid.setConfig({
				target: $('[data-ax5grid="CSGrid"]'),
				showRowSelector: true,
				showLineNumber: false,
				multipleSelect: true,
				virtualScrollY: false,
				lineNumberColumnWidth: 40,
				rowSelectorColumnWidth: 27,
				columns: [
					{key: "title", label: "제목", align: "center"},
					{key: "postNum", label: "게시글 수", formatter: "", align: "center"},//매거진 여부 밑 종류
					{key: "image", label: "이미지", align: "center"}
				],
				header: {
					selector: false
				},
				body: {
					onClick: function () {
						console.log(this);
					},
					onDataChanged: function () {
						console.log(this);
					}
				}
			}); 
			
			$('[data-grid-control]').click(function () {
				switch (this.getAttribute("data-grid-control")) {
				case "row-add":
					UserGrid.addRow($.extend({}, UserGrid.list[Math.floor(Math.random() * UserGrid.list.length)], {__index: undefined}));
					break;
				case "row-remove":
					for(let i=0; i<=UserGrid.list.length-1; i++) {
						if(UserGrid.list[i].__selected__==true) {
							UserGrid.removeRow(i);
						}
					}
					alert("삭제되었습니다!");
					break;
				case "row-update":
					var updateIndex = Math.floor(Math.random() * UserGrid.list.length);
					UserGrid.updateRow($.extend({}, UserGrid.list[updateIndex], {price: 100, amount: 100, cost: 10000}), updateIndex);
					alert("나중에... 딴 거 다 하고 나서...");
					break;
				}
			});
			
			/* 테스트용 데이터 생성 */
			/*for (var i = 0; i < 50; i++) {
				UserList.push(
					{reg_date: "20100101", u_name: "홍길동"+(i+1),  b_date: "20170101", u_id: "@00"+i, phone: "010-1234-5678", seller:"일반회원"}
				);
			}
			for (var i = 0; i < 50; i++) {
				CSList.push(
					{writedate: "20100101", nickname: "홍길동"+(i+1), u_id: "@00"+i, title: "제목"+i, content: "a"}
				);
			}
			for (var i = 0; i < 50; i++) {
				RecipePostList.push(
					{writedate: "20100101", title:"제목"+i, nickname: "홍길동"+i, recipe_id: i}
				);
			}
			for (var i = 0; i < 50; i++) {
				SearchTermsList.push(
					{what: "a", title:"이름"+i, nickname: "홍길동"+i, recipe_id: i}
				);
			}
			for (var i = 0; i < 50; i++) {
				MagazineList.push(
					{writedate: "20100101", title:"제목"+i, nickname: "홍길동"+i, recipe_id: i}
				);
			}*/

			/* 그리드에 데이터리스트 설정 */
			//UserGrid.setData(UserList);
			RecipePostGrid.setData(RecipePostList);
			//CSGrid.setData(CSList);
			SearchTermsGrid.setData(SearchTermsList);
			//MagazineGrid.setData(MagazineList);
		});  // end of the $(function).
			
		

