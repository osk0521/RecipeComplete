$(function() {
	
	let all_today = new Date();
	let all_yesterday= new Date(Date.parse(all_today)-1*1000*60*60*24);
	let all_7day= new Date(Date.parse(all_today)-7*1000*60*60*24);
	let all_30day= new Date(Date.parse(all_today)-30*1000*60*60*24);
	
	let today_year = all_today.getFullYear();
	let today_month = all_today.getMonth()+1;
	let today_date = all_today.getDate();
	let origin_today = today_year + "-" + today_month + "-" + today_date;
	
	
	let y_year = all_yesterday.getFullYear();
	let y_month = all_yesterday.getMonth()+1;
	let y_date = all_yesterday.getDate();
	let yesterday = y_year + "-" + y_month + "-" + y_date;
	
	let w_year = all_7day.getFullYear();
	let w_month = all_7day.getMonth()+1;
	let w_date = all_7day.getDate();
	let week = w_year + "-" + w_month + "-" + w_date;
	
	let m_year = all_30day.getFullYear();
	let m_month = all_30day.getMonth()+1;
	let m_date = all_30day.getDate();
	let month = m_year + "-" + m_month + "-" + m_date;
	
	
	$("#select_all").click(function(){
		let check = $("#select_all").is(':checked');
		if(check)
			$('input:checkbox').prop('checked',true);
		else
			$('input:checkbox').prop('checked',false);
	})
	
	$("#datebtn_today").click(function(){
		$('#date_from').val(origin_today);
		$('#date_to').val(origin_today);
	})
	
	$("#datebtn_yesterday").click(function() {
		$('#date_from').val(yesterday);
		$('#date_to').val(origin_today);
	})
	
	$("#datebtn_week").click(function() {
		$('#date_from').val(week);
		$('#date_to').val(origin_today);
	})
	
	$("#datebtn_month").click(function() {
		$('#date_from').val(month);
		$('#date_to').val(origin_today);
	})
	
})