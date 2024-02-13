$(function() {
	$("#select_all").click(function(){
		let check = $("#select_all").is(':checked');
		if(check)
			$('input:checkbox').prop('checked',true);
		else
			$('input:checkbox').prop('checked',false);
	})
})