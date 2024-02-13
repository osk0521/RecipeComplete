/*//카테고리 설정
function setCategory(){
    var json_category = [];
    for (var i = 1; i <= 4; i++) {
        var id = $("[id^=spanCategory_" + i + "_]").filter('.label-success').prop('id');
        var cate_code = id.replace('spanCategory_' + i + '_', '');
        $("#cok_sq_category_" + i).val(cate_code);
    }
    closeCategory();
}

function closeCategory(){
    $("#btnAllCategory").popover('hide');
}

//재료 묶음 추가
function addMaterialGroup(title,json,group_idx,isManualAdd) {
	var is_exist_group = false;
	if (group_idx && $("#divMaterialArea_"+group_idx).length) {
		//존재함
		is_exist_group = true;
	} else {
		if (!group_idx) group_idx = 0;
		$("[id^=divMaterialArea_]").each(function() {
            var idx = parseInt($(this).prop('id').replace('divMaterialArea_',''),10);
            group_idx = Math.max(group_idx,idx);
        });
        group_idx++;
	}
	if (is_exist_group) {
		var prev_title = $("#liMaterialGroup_"+group_idx+" [id=material_group_title_"+group_idx+"]").val();
		if ((prev_title == '' || prev_title == '재료') && title != '') {
			$("#liMaterialGroup_"+group_idx+" [id=material_group_title_"+group_idx+"]").val(title);
		}
	} else {
		var title_width = ($("#cok_reg_type").val() == 'edit') ? 190 : 210;
		var addbtn_style = ($("#cok_reg_type").val() == 'edit') ? 'padding:0 0 20px 240px; width:600px;' : 'padding:0 0 20px 350px; width:800px;';
		var str = '';
        str += '<li id="liMaterialGroup_'+group_idx+'">';
        str += ($("#cok_reg_type").val() == 'edit') ? '<p class="cont_tit6">' : '<p class="cont_tit6 st2 mag_r_15">';
        str += '<a href="#" class="btn-lineup"></a>';
		str += '<input type="text" name="material_group_title_'+group_idx+'" id="material_group_title_'+group_idx+'" value="'+title+'" class="form-control" style="font-weight:bold;font-size:18px;width:'+title_width+'px;">';
        str += '</p>';
        str += '<ul id="divMaterialArea_'+group_idx+'"></ul>';
        str += '<div class="btn_add" style="'+addbtn_style+'"><button type="button" onclick="addMaterial('+group_idx+')" class="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span>추가</button></div>';
        str += '</li>';
        str += '<div id="divMaterialArea2_'+group_idx+'" style="display: flex; justify-content: flex-end;margin:-20px 0 30px 0;">';
        str += '<span class="cont_tit_btn"><button type="button" onclick="delMaterialGroup('+group_idx+')" class="btn-sm btn-default"><span class="glyphicon glyphicon-minus"></span> 재료 묶음 삭제</button></span>';
        str += '</div>';
        $(str).appendTo("#divMaterialGroupArea");
	}

    for (var i=0; i<json.length; i++) {
		addMaterial(group_idx,json[i],'');
	}
	if (group_idx == 1) {
		if ($("#divMaterialArea_" + group_idx + " [id^=liMaterial_" + group_idx + "_]").length < 3) {
			for (var j = i; j < 3; j++) {
				addMaterial(group_idx, [], '');
			}
		}
	} else {
		if ($("#divMaterialArea_" + group_idx + " [id^=liMaterial_" + group_idx + "_]").length < 3) {
			for (var j = i; j < 2; j++) {
				addMaterial(group_idx, [], '');
			}
		}
	}
	$("#divMaterialGroupArea").sortable({
        handle: $('.btn-lineup')
    });
	if (isManualAdd && isManualAdd == '1') {
        $("#material_group_title_"+group_idx).focus();
    }
}

//재료 묶음 삭제
function delMaterialGroup(group_idx) {
	var cnt = 0;
	$("#divMaterialArea_"+group_idx+" [id^=cok_material_nm_"+group_idx+"_]").each(function() {
		if ($.trim($(this).val()) != '') {
			cnt++;
		}
	});
	var isOK = true;
	if (cnt > 0) {
		if(!confirm('['+$("#material_group_title_"+group_idx).val()+']을 삭제하시겠습니까?')) {
			isOK = false;
		}
	}
	if (isOK) {
		if ($("#divMaterialGroupArea [id^=liMaterialGroup_]").length == 1) {
            $("#liMaterialGroup_"+group_idx+" [id=material_group_title_"+group_idx+"]").val('');
            $("#divMaterialArea_"+group_idx+" [id^=liMaterial_"+group_idx+"_]").each(function(idx,obj) {
                var step = $(this).prop('id').replace('liMaterial_'+group_idx+'_','');
                if (idx < 3) {
                    $("#liMaterial_"+group_idx+"_"+step+" [id=cok_material_nm_"+group_idx+"_"+step).val('');
                    $("#liMaterial_"+group_idx+"_"+step+" [id=cok_material_amt_"+group_idx+"_"+step).val('');
                } else {
                    $("#liMaterial_"+group_idx+"_"+step).remove();
                }
            });
        } else {
            $("#divMaterialGroupArea [id=liMaterialGroup_"+group_idx+"]").fadeOut(200,function() {
                $(this).remove();
                $("#divMaterialArea2_"+group_idx).remove();
            });
        }
	}
}

//재료 추가
function addMaterial(group_idx, init_json, prev_step){
    var step = 0;
    $("#divMaterialArea_"+group_idx+" [id^=liMaterial_"+group_idx+"_]").each(function(){
        var tmp = $(this).prop('id').replace('liMaterial_'+group_idx+'_', '');
        var tmp_step = parseInt(tmp, 10);
        step = Math.max(step, tmp_step);
    });
    step++;
    //alert(step);
    var w1 = ($("#cok_reg_type").val() == 'edit') ? 180 : 330;
	var w2 = ($("#cok_reg_type").val() == 'edit') ? 140 : 280;
    var str = '';
	str += '<li id="liMaterial_'+group_idx+'_'+step+'"><a href="#" class="btn-lineup"></a>';
    str += '<input type="text" name="cok_material_nm_'+group_idx+'[]" id="cok_material_nm_'+group_idx+'_'+step+'" class="form-control" style="width:'+w1+'px;">';
    str += '<input type="text" name="cok_material_amt_'+group_idx+'[]" id="cok_material_amt_'+group_idx+'_'+step+'" class="form-control" style="width:'+w2+'px;">';
    str += '<a id="btnMaterialDel_'+group_idx+'_'+step+'" href="javascript:delMaterial('+group_idx+','+step+')" class="btn-del" style="display:none"></a></li>';

    if (typeof prev_step == 'undefined' || prev_step === null || prev_step == 0) {
        $(str).appendTo('#divMaterialArea_'+group_idx);
    }
    else {
        $(str).insertAfter("#liMaterial_"+group_idx+"_" + prev_step);
    }

    if (typeof init_json !== 'undefined' && init_json !== null && init_json['mat_nm_material']) {
        $("#divMaterialArea_"+group_idx+" [id=cok_material_nm_" + group_idx + "_" + step + "]").val(init_json['mat_nm_material']);
    } else {
        $("#divMaterialArea_"+group_idx+" [id=cok_material_nm_" + group_idx + "_" + step + "]").attr('placeholder','예) '+_MATERIAL_SAMPLE[(step-1)%_MATERIAL_SAMPLE.length]['mat_nm_material']);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && (init_json['mat_no_amount'] || init_json['mat_tx_amount'])) {
		$("#divMaterialArea_"+group_idx+" [id=cok_material_amt_" + group_idx + "_" + step + "]").val((init_json['mat_no_amount'] ? init_json['mat_no_amount'] : '')+(init_json['mat_tx_amount'] ? init_json['mat_tx_amount'] : ''));
    } else {
        $("#divMaterialArea_"+group_idx+" [id=cok_material_amt_" + group_idx + "_" + step + "]").attr('placeholder','예) '+_MATERIAL_SAMPLE[(step-1)%_MATERIAL_SAMPLE.length]['mat_nm_amount']);
    }

    $("#divMaterialArea_"+group_idx+" [id=liMaterial_" + group_idx + "_" + step + "]").mouseover(function(){
        $(this).find('.btn-del').show();
    }).mouseout(function(){
        $(this).find('.btn-del').hide();
    });

    $("#divMaterialArea_"+group_idx).sortable({
        handle: $('.btn-lineup')
    });
    //$( "ul, li" ).disableSelection();
}
//재료 삭제
function delMaterial(group_idx,step) {
    $("#divMaterialArea_"+group_idx+" [id=liMaterial_"+group_idx+"_"+step+"]").fadeOut(200,function() {
        $(this).remove();
    });
}
//이게 뭐지?
function addSpice(prev_step, init_json){
    var step = 0;

    $("#divSpiceArea [id^=liSpice_]").each(function(){
        var tmp = $(this).prop('id').replace('liSpice_', '');
        var tmp_step = parseInt(tmp, 10);
        step = Math.max(step, tmp_step);
    });
    step++;
    //alert(step);
	var w = ($("#cok_reg_type").val() == 'edit') ? 190 : 300;
    var str = '<li id="liSpice_'+step+'"><a href="#" class="btn-lineup"></a>';
    str += '<input type="text" name="cok_spice_nm[]" id="cok_spice_nm_'+step+'" class="form-control" style="width:'+w+'px;">';
    str += '<input type="text" name="cok_spice_amt[]" id="cok_spice_amt_'+step+'" class="form-control" style="width:'+w+'px;">';
    str += '<a id="btnSpiceDel_'+step+'" href="javascript:delSpice('+step+')" class="btn-del" style="display:none"></a></li>';

    if (typeof prev_step == 'undefined' || prev_step === null || prev_step == 0) {
        $(str).appendTo('#divSpiceArea');
    }
    else {
        $(str).insertAfter("#liSpice_" + prev_step);
    }

    if (typeof init_json !== 'undefined' && init_json !== null && init_json['nm']) {
        $("#divSpiceArea [id=cok_spice_nm_" + step + "]").val(init_json['nm']);
    } else {
        $("#divSpiceArea [id=cok_spice_nm_" + step + "]").attr('placeholder','예) '+_SPICE_SAMPLE[(step-1)%_SPICE_SAMPLE.length]['nm']);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && init_json['amt']) {
        $("#divSpiceArea [id=cok_spice_amt_" + step + "]").val(init_json['amt']);
    } else {
        $("#divSpiceArea [id=cok_spice_amt_" + step + "]").attr('placeholder','예) '+_SPICE_SAMPLE[(step-1)%_SPICE_SAMPLE.length]['amt']);
    }

    $("#divSpiceArea [id=liSpice_" + step + "]").mouseover(function(){
        $(this).find('.btn-del').show();
    }).mouseout(function(){
        $(this).find('.btn-del').hide();
    });

	$("#divSpiceArea").sortable({
        handle: $('.btn-lineup')
    });
	//$( "ul, li" ).disableSelection();
}
function delSpice(step) {
    $("#divSpiceArea [id=liSpice_"+step+"]").fadeOut(200,function() {
        $(this).remove();
    });
}

//순서 추가
function addStep(prev_step, init_json){
    var step = 0;
    //var obj_step = $(obj).parent().prop('id').replace('divStepBtn_','');
    $("#divStepArea [id^=divStepItem_]").each(function(){
        var tmp = $(this).prop('id').replace('divStepItem_', '');
        var tmp_step = parseInt(tmp, 10);
        step = Math.max(step, tmp_step);
    });
    step++;
    //alert(step);
    var str = $("#divStepTemplate").html().replace(/__STEP/g, step);
    var str = str.replace(/_STEP/g, '_' + step);


    if (typeof prev_step == 'undefined' || prev_step === null || prev_step == 0) {
        $(str).appendTo('#divStepArea');
    }
    else {
        $(str).insertAfter("#divStepItem_" + prev_step);
    }

    if (typeof init_json !== 'undefined' && init_json !== null && init_json['text']) {
        $("#divStepArea [id=step_text_" + step + "]").val(init_json['text']);
    } else {
		$("#divStepArea [id=step_text_" + step + "]").attr('placeholder','예) '+_STEP_SAMPLE[(step-1)%_STEP_SAMPLE.length]);
	}
    if (typeof init_json !== 'undefined' && init_json !== null && init_json['step_no']) {
        $("#divStepArea [id=step_no_" + step + "]").val(init_json['step_no']);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && init_json['photo']) {
        setStepPhoto('', init_json['photo'][0], init_json['photo'][0], step);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && (init_json['tip'] || init_json['material'] || init_json['fire'] || init_json['cooker'] || init_json['video'])) {
        $("#divStepArea [id=addStepInfoForm_" + step + "]").show();
    } else {
        $("#divStepArea [id=addStepInfoButs_" + step + "]").show();
    }

    if (typeof init_json !== 'undefined' && init_json !== null && init_json['tip']) {
        $("#divStepArea [id=stepForm_tip_" + step + "]").show();
        $("#divStepArea [id=step_tip_" + step + "]").val(init_json['tip']);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && init_json['material']) {
        $("#divStepArea [id=stepForm_material_" + step + "]").show();
        $("#divStepArea [id=step_material_" + step + "]").val(init_json['material']);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && init_json['fire']) {
        $("#divStepArea [id=stepForm_fire_" + step + "]").show();
        $("#divStepArea [id=step_fire_" + step + "]").val(init_json['fire']);
    }
    if (typeof init_json !== 'undefined' && init_json !== null && init_json['cooker']) {
        $("#divStepArea [id=stepForm_cooker_" + step + "]").show();
        $("#divStepArea [id=step_cooker_" + step + "]").val(init_json['cooker']);
    }

    if (typeof init_json !== 'undefined' && init_json !== null && init_json['video']) {
        $("#divStepArea [id=stepForm_video_" + step + "]").show();
        $("#divStepArea [id=step_video_" + step + "]").val(init_json['video']);
        $("#divStepArea [id=step_video_seq_" + step + "]").val(init_json['video_seq']);
    }

    $("#divStepArea [id=stepBtn_material_" + step + "]").click(function(){
        $("#divStepArea [id=stepForm_material_" + step + "]").show();
    });
    $("#divStepArea [id=stepBtn_cooker_" + step + "]").click(function(){
        $("#divStepArea [id=stepForm_cooker_" + step + "]").show();
    });
    $("#divStepArea [id=stepBtn_fire_" + step + "]").click(function(){
        $("#divStepArea [id=stepForm_fire_" + step + "]").show();
    });
    $("#divStepArea [id=stepBtn_tip_" + step + "]").click(function(){
        $("#divStepArea [id=stepForm_tip_" + step + "]").show();
    });
    $("#divStepArea [id=stepBtn_video_" + step + "]").click(function(){
        $("#divStepArea [id=stepForm_video_" + step + "]").show();
    });
    $("#divStepArea [id=stepBtn_all_" + step + "]").click(function(){
        $("#divStepArea [id=stepForm_material_" + step + "]").show();
        $("#divStepArea [id=stepForm_cooker_" + step + "]").show();
        $("#divStepArea [id=stepForm_fire_" + step + "]").show();
        $("#divStepArea [id=stepForm_tip_" + step + "]").show();
            });

    $("#divStepArea [id=divStepItem_" + step + "]").mouseover(function(){
        $(this).find('.step_btn').show();
    }).mouseout(function(){
        $(this).find('.step_btn').hide();
    });

    $("#divStepArea [id=divStepItem_" + step + "] .moveUp").click(function(){
		if ($(this).parents('.step').prevAll('.step').length !== 0) {
			$(this).parents('.step').insertBefore($(this).parents('.step').prev());
			remakeStepNumber();
		}
    });
    $("#divStepArea [id=divStepItem_" + step + "] .moveDown").click(function(){
		if ($(this).parents('.step').nextAll('.step').length !== 0) {
			$(this).parents('.step').insertAfter($(this).parents('.step').next());
			remakeStepNumber();
		}
    });
	$("#divStepArea").sortable({
        handle: ($("#cok_reg_type").val() == 'input') ? $(".cont_tit2_1") : $(".cont_tit2"),
        stop: function(event,ui) {
            remakeStepNumber();
        }
    });

	if ($("#cok_reg_type").val() == 'edit') {
		$("#divStepItem_"+step).droppable({
            accept: "#divLeftContent img, #divLeftContent span",
			drop: function( event, ui ) {
				//var src = ui.draggable.attr('src');
				var src = ($(ui.draggable).prop('tagName') == 'IMG') ? ui.draggable.attr('src') : ui.draggable.attr('img_src');
                var target_step = $(this).prop('id').replace('divStepItem_','');
				setStepPhoto('1',src,src,target_step);
            }
        });
	}

    bindEvent(document.getElementById("q_step_file_" + step), 'change', handlePhotoFiles);

    remakeStepNumber();
}

function bindEvent(el, eventName, eventHandler){

    if (el.addEventListener) {
        el.addEventListener(eventName, eventHandler, false);
    }
    else{
        if (el.attachEvent) {
            el.attachEvent(eventName, eventHandler);
        }
	}
}


function remakeStepNumber(){
    $("#divStepArea [id^=divStepItem_]").each(function(idx, obj){
        var step = $(this).prop('id').replace('divStepItem_', '');
        $("#divStepArea [id=divStepNum_" + step + "]").html('Step' + (idx + 1));
    });
}

function browseContentsFile() {
	var editor=oEditors.getById["boa_tx_content"];
    if (editor) {
        $("#q_contents_file").click();
    } else {
        alert('글쓰기 폼이 준비되지 않았습니다. 다시 시도해 주세요.');
    }
}

function browseMainFile(){
    $("#q_main_file").click();
}

function browseVideoFile(){
    $("#q_video_file").click();
}

function browseStepFile(step){
    $("#divStepArea [id=q_step_file_" + step + "]").click();
}

function browseWorkFile(num){
    $("#divWorkArea [id=q_work_file_" + num + "]").click();
}
//순서 삭제
function delStep(step){
	$("#divStepArea [id=divStepItem_" + step + "]").fadeOut(200,function() {
		$("#divStepArea [id=divStepItem_" + step + "]").remove();
		remakeStepNumber();
	});
}
function adjustStep(step) {
    adjustText('step_text_'+step);
}
function adjustText(id) {
    var arr_str = [];
    var contents = $('#'+id).val();
    if ($.trim(contents) != '') {
        var temp = contents.split('\n');
        for (var i = 0; i < temp.length; i++) {
            if ($.trim(temp[i]) != '') {
                arr_str.push($.trim(temp[i]));
            }
        }
        $('#' + id).val(arr_str.join(' '));
    }
}

var isSubmit = false;
function doSubmit(save_type)
{
    $("#save_type").val(save_type);

    chkResult = validateRecipeForm(save_type);
    if (!chkResult) {
        return isSubmit = false;
    }
    if (save_type == 'save') {
        if (confirm("저장하시겠습니까?")) {
            isSubmit = true;
            $("#insFrm").submit();
        }
        else {
            isSubmit = false;
        }
    } else if (save_type == 'save_public') {
				var msg = '레시피 공개 후, 리스트 및 검색에 노출되는 데는 하루 정도의 시간이 소요됩니다.\n\n레시피를 공개하시겠습니까?';
		        if (confirm(msg)) {
            isSubmit = true;
            $("#insFrm").submit();
        }
        else {
            isSubmit = false;
        }
    } else if (save_type == 'save_work' || save_type == 'save_confirm') {
        isSubmit = true;
        $("#insFrm").submit();
    } else {
        isSubmit = false;
    }
}
function validateRecipeForm(save_type) {
    if ($.trim($("#cok_title").val()) == '') {
        alert('레시피 제목을 입력해 주세요.');
        $("#cok_title").focus();
        return isSubmit = false;
    }

    if (save_type != 'save') {
        if ($("#main_photo").val() == '') {
            alert('대표사진을 선택해 주세요.');
            return isSubmit = false;
        }
        if ($.trim($("#cok_intro").val()) == '') {
            alert('요리소개 내용을 입력해 주세요.');
            $("#cok_intro").focus();
            return isSubmit = false;
        }
        if ($("#cok_sq_category_1").val() == '') {
            alert('방법별 카테고리를 선택해 주세요.');
            $("#cok_sq_category_1").focus();
            return isSubmit = false;
        }
        if ($("#cok_sq_category_2").val() == '') {
            alert('상황별 카테고리를 선택해 주세요.');
            $("#cok_sq_category_2").focus();
            return isSubmit = false;
        }
        if ($("#cok_sq_category_3").val() == '') {
            alert('재료별 카테고리를 선택해 주세요.');
            $("#cok_sq_category_3").focus();
            return isSubmit = false;
        }
        if ($("#cok_sq_category_4").val() == '') {
            alert('종류별 카테고리를 선택해 주세요.');
            $("#cok_sq_category_4").focus();
            $("#btnAllCategory").trigger('click');

            return isSubmit = false;
        }
		if ($("#is_tv_recipe").prop('checked')) {

		} else {
			if ($("#cok_portion").val() == '') {
                alert('요리인원 선택해 주세요.');
                $("#cok_portion").focus();
                return isSubmit = false;
            }
			if ($("#cok_time").val() == '') {
                alert('요리시간을 선택해 주세요.');
                $("#cok_time").focus();
                return isSubmit = false;
            }
            if ($("#cok_degree").val() == '') {
                alert('난이도를 선택해 주세요.');
                $("#cok_degree").focus();
                return isSubmit = false;
            }
		}

        var resource_cnt = 0;
		$("#divResourceArea [id^=liResource_]").each(function(i) {
            var step = $(this).prop('id').replace('liResource_','');
            if ($.trim($("#cok_resource_nm_" + step).val()) != '') {
                resource_cnt++;
            }
        });
		var invalid_resource = false;
        $("[id^=cok_material_nm_]").each(function() {
			if ($.trim($(this).val()) != '') {
                resource_cnt++;
            }
			var idx = $(this).prop('id').replace('cok_material_nm_','');
			if ($(this).val().indexOf('[') > -1 || $(this).val().indexOf(']') > -1) {
				invalid_resource = true;
				$(this).focus();
				return false;
			}
			if ($("#cok_material_amt_"+idx).val().indexOf('[') > -1 || $("#cok_material_amt_"+idx).val().indexOf(']') > -1) {
				invalid_resource = true;
				$("#cok_material_amt_"+idx).focus();
                return false;
            }
		});
		if (invalid_resource) {
			alert('요리재료에 [ 또는 ] 문자를 입력할 수 없습니다.');
			return isSubmit = false;
		}
        if (resource_cnt < 1) {
            alert('요리재료는 최소 1개 이상이어야 합니다.');
			if ($("#divResourceArea [id^=liResource_]").length > 0) {
				$("#divResourceArea > li:last-child").find('input')[0].focus();
			} else {
				$("[id^=cok_material_nm_]:first").focus();
			}
            return isSubmit = false;
        }

        var step_cnt = 0;
        var invalid_step = 0;
        $("#divStepArea [id^=divStepItem_]").each(function(i) {
            var step = $(this).prop('id').replace('divStepItem_','');
            if ($("#step_photo_"+step).val() != '' && $.trim($("#step_text_" + step).val()) == '') {
                alert("내용을 입력하세요.");
                $("#step_text_" + step).focus();
                invalid_step = step;
                return false;
            } else if ($.trim($("#step_text_" + step).val()) != '') {
                step_cnt++;
            }
        });
        if (invalid_step > 0) {
            return isSubmit = false;
        }
        if (step_cnt < 3) {
            alert('요리순서는 최소 3개 이상이어야 합니다.');
            $("#divStepArea textarea").last().focus();
            return isSubmit = false;
        }

    }
    return true;
}

	function delRecipe(idx) {
	    $("#trRecipeRow_"+idx).remove();
	    if (!$("[id^=trRecipeRow_]").length) {
	        $("#trNoRecipe").show();
	    }
	}
});

 var cache = {};
    $("#mySingleFieldTags").tagit({
        singleField: true,
        singleFieldNode: $('#mySingleFieldTags'),
        singleFieldDelimiter: ',',
        allowSpaces: true,
        afterTagAdded : function(event, ui) {
            // limit length
            var tArr = $("#mySingleFieldTags").tagit("assignedTags");
            if(tArr.length > 10)
            {
                alert('태그는 10개까지만 작성이 가능합니다.');
                $("#mySingleFieldTags").tagit("removeTagByLabel", tArr[tArr.length - 1]);
            }
        },
        autocomplete : {
            //minLength : 2,
            source: function( request, response ) {
                var term = request.term;
                if ( term in cache ) {
                    response( cache[ term ] );
                    return;
                }
                $.getJSON( "/util/autocomplete.html?q_mode=tag", request, function( data, status, xhr ) {
                    cache[ term ] = data;
                    response( data );
                });
            }
        }
    });*/