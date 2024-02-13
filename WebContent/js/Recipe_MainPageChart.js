$(function() {
	// id: chart-container
	 var myChart = echarts.init(document.getElementById('chart-container'));

      var option = {
		title: {
			text: ''
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['방문자수', '신규회원수']
		},
		xAxis: {
			data: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		},
        yAxis: {
			type: 'value'
        },
        series: [
          {
            name: '방문자수',
            type: 'line',
            data: [4, 7, 8, 3, 12, 2, 10, 11, 9, 6, 1, 5]
          },
          {
            name: '신규회원수',
            type: 'line',
            data: [6, 8, 1, 4, 5, 3, 2, 9, 12, 7, 10, 11]
          }

        ]
      };
/* 		      월	사이트 방문자	신규 회원 가입
		      1		4	6
		      2		7	8
		      3		8	1
		      4		3	4
		      5		12	5
		      6		2	3
		      7		10	2
		      8		11	9
		      9		9	12
		      10	6	7
		      11	1	10
		      12	5	11
 */		    
      myChart.setOption(option);
});