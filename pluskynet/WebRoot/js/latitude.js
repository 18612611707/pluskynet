$(function() {
	//页面加载完成之后执行
	pageInit();
});
function pageInit() {
	var lastsel;
	//创建jqGrid组件
	jQuery("#list2").jqGrid(
		{
			url : '../LatitudeauditAction!getLatitudeList.action', //组件创建完成之后请求数据的url
			datatype : "json", //请求数据返回的类型。可选json,xml,txt
			colNames : [ 'id', '规则id', '规则类型', '规则名称', '总数', '符合数', '不符合数', '跑批状态' ], //jqGrid的列显示名字
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
					name : 'causename',
					index : 'causename',
					width : 100,
					sortable : false
				},
				{
					name : 'sunnum',
					index : 'sunnum',
					width : 100,
					sortable : false
				},
				{
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
				},
				{
					name : 'rulestats',
					index : 'rulestats',
					width : 100,
					sortable : false /*,
					editable : true 行编辑*/
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
	var s;
	jQuery("#m1").click(function() {
		s = jQuery("#list2").jqGrid('getGridParam', 'selarrrow');
		var latitudeids;
		for (var int = 0; int < s.length; int++) {
			if(latitudeids == "" || latitudeids == null || latitudeids == undefined){
				latitudeids = s[int];
			}else{
				latitudeids = latitudeids+","+s[int];
			}
		}
		$.ajax({
			type : "post",
			url : '../LatitudeauditAction!updateStats.action',
			dataType : "json",
			async : false,
			data:{
				latitudeids:latitudeids
			},
			success : function(data) {
				if (data) {
					datas = data;
				}
			}
		});
		pageInit();
	});
}