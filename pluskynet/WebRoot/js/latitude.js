$(function() {
	//页面加载完成之后执行
	pageInit();
/*	docnum();
	lanum();*/
});
function docnum() {
	$.ajax({
		type : "post",
		url : '../BatchStatisticalAction!docStatistical.action',
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {
				if (data.msg == "成功") {
					var s = data.data;
					for (var int = 0; int < s.length; int++) {
						console.info(s[int]);
						console.info(s[int].name);
						console.info(s[int].nums);
						if (s[int].name == "docnum") {
							$("#docnum").attr("value", s[int].nums);
						} else {
							$("#docnums").attr("value", s[int].nums);
						}
					}
				}
			}
		}
	});

}
function lanum() {
	$.ajax({
		type : "post",
		url : '../BatchStatisticalAction!laStatistical.action',
		dataType : "json",
		async : true,
		success : function(data) {
			if (data) {
				if (data.msg == "成功") {
					var s = data.data;
					for (var int = 0; int < s.length; int++) {
						console.info(s[int]);
						console.info(s[int].name);
						console.info(s[int].nums);
						if (s[int].name == "lanum") {
							$("#lanum").attr("value", s[int].nums);
						} else {
							$("#lanums").attr("value", s[int].nums);
						}
					}
				}
			}
		}
	});
}
function pageInit() {
	var lastsel;
	//创建jqGrid组件
	jQuery("#list2").jqGrid(
		{
			url : '../LatitudeauditAction!getLatitudeList.action', //组件创建完成之后请求数据的url
			datatype : "json", //请求数据返回的类型。可选json,xml,txt
			colNames : [ 'id', '规则id', '规则类型','父规则名称', '规则名称','提交时间', '审核状态' ], //jqGrid的列显示名字
			colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
				{
					name : 'id',
					index : 'id',
					width : 55,
					sortable : false
				},
				{
					name : 'latitudeid',
					index : 'latitudeid',
					width : 55,
					sortable : false
				},
				{
					name : 'latitudetype',
					index : 'latitudetype',
					width : 100,
					sortable : false
				},
				{
					name : 'fcasename',
					index : 'fcasename',
					width : 100,
					sortable : false
				},
				{
					name : 'causename',
					index : 'causename',
					width : 100,
					sortable : false
				},
				{
					name : 'subtime',
					index : 'subtime',
					width : 100,
					sortable : false
				},
				/*{
					name : 'cornum',
					index : 'cornum',
					width : 100,
					sortable : false
				},
				{
					name : 'ncornum',
					index : 'ncornum',
					width : 100,
					sortable : false
				},*/
				{
					name : 'rulestats',
					index : 'rulestats',
					width : 100,
					sortable : false,
					editable : true //行编辑
				}
			],
			/*rowNum : 10,//一页显示多少条
			rowList : [ 10, 20, 30 ],//可供用户选择一页显示多少条
*/
			pager : '#pager2', //表格页脚的占位符(一般是div)的id
			mtype : "post", //向后台请求数据的ajax的类型。可选post,get
			//				viewrecords : true,// 定义是否在导航条上显示总的记录数
			loadonce : true,
			scroll : true,
			height : "600",
			width : "1500",
			viewrecords : true,
			multiselect : true,
			jsonReader : {
				root : 'data', // 注意这里 详细请到官方查看
				/*	total : 'totalPage', // 总页数
					page : 'page', // 页码
					records : 'totalSize', // 总记录数
					,
					repeatitems : false*/
				id : "id"
			},
			/*	onSelectRow : function(id) { 行编辑
					if (id && id !== lastsel) {
						jQuery('#list2').jqGrid('restoreRow', lastsel);
						jQuery('#list2').jqGrid('editRow', id, {
							keys:true,
							url:'../LatitudeauditAction!updateStats.action',
							mtype: "post",
							prams: {
								"latitudeid": $("#"+id+"_latitudeid").val(),
								"stats": $("#"+id+"_rulestats").val(),
								"latitudetype":$("#"+id+"_latitudetype").val()
								"ware.warename": $("#"+id+"_name").val(),
								"ware.createDate": $("#"+id+"_date").val(),
								"ware.number": $("#"+id+"_amount").val(),
								"ware.valid": $("#"+id+"_type").val()
							}
						});
						lastsel = id;
					}
				},*/
			//			editurl : '../LatitudeauditAction!updateStats.action',
			caption : "Reason count" //表格的标题名字
		});
	/*创建jqGrid的操作按钮容器*/
	/*可以控制界面上增删改查的按钮是否显示*/
	jQuery("#list2").jqGrid('navGrid', '#pager2', {
		edit : false,
		add : false,
		del : false
	});
	jQuery("#m1").click(function() {
		var s;
		s = jQuery("#list2").jqGrid('getGridParam', 'selarrrow');
		var latitudeids;
		for (var int = 0; int < s.length; int++) {
			if (latitudeids == "" || latitudeids == null || latitudeids == undefined) {
				latitudeids = s[int];
			} else {
				latitudeids = latitudeids + "," + s[int];
			}
		}
		$.ajax({
			type : "post",
			url : '../LatitudeauditAction!updateStats.action',
			dataType : "json",
			async : false,
			data : {
				latitudeids : latitudeids
			},
			success : function(data) {
				if (data) {
					alert(data.msg);
					if (data.msg == "成功") {
						$("#m2").attr("disabled", false);
						$("#m3").attr("disabled", false);
						$("#m4").attr("disabled", false);
						$("#m5").attr("disabled", false);
					}
				}
			}
		});
	});
	jQuery("#m2").click(function() {
		$.ajax({
			type : "post",
			url : '../DocRuleAction!Docrun.action',
			dataType : "json",
			async : false,
			success : function(data) {
				if (data) {
					alert(data.msg);
				}
			}
		});
	});
	jQuery("#m3").click(function() {
		$.ajax({
			type : "post",
			url : '../LatitudeauditAction!latitudeRun.action',
			dataType : "json",
			async : false,
			success : function(data) {
				if (data) {
					alert(data.msg);
				}
			}
		});
	});
	jQuery("#m4").click(function() {
		$.ajax({
			type : "post",
			url : '../LatitudeauditAction!newDocrun.action',
			dataType : "json",
			async : false,
			success : function(data) {
				if (data) {
					alert(data.msg);
				}
			}
		});
	});
	jQuery("#m5").click(function() {
		$.ajax({
			type : "post",
			url : '../LatitudeauditAction!newlatitudeRun.action',
			dataType : "json",
			async : false,
			success : function(data) {
				if (data) {
					alert(data.msg);
				}
			}
		});
	});
}