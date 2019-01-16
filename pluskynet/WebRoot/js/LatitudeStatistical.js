$(function() {
	//页面加载完成之后执行
	pageInit();
});
function pageInit() {
	var lastsel;
	//创建jqGrid组件
	jQuery("#list2").jqGrid(
		{
			url : '../PreviewhisAction!latitudestatistical.action', //组件创建完成之后请求数据的url
			datatype : "json", //请求数据返回的类型。可选json,xml,txt
			colNames : [ 'id', '维度id', '维度父级名称', '名称纬度', '数量', '段落名称', '段落数量' ], //jqGrid的列显示名字
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
					name : 'latitudefname',
					index : 'latitudefname',
					width : 100,
					sortable : false
				},
				{
					name : 'latitudename',
					index : 'latitudename',
					width : 100,
					sortable : false
				},
				{
					name : 'latitudenums',
					index : 'latitudenums',
					width : 100,
					sortable : false
				},
				{
					name : 'sectionname',
					index : 'sectionname',
					width : 100,
					sortable : false
				},
				{
					name : 'sectionnums',
					index : 'sectionnums',
					width : 100,
					sortable : false
				}
			],
			pager : '#pager2', //表格页脚的占位符(一般是div)的id
			mtype : "post", //向后台请求数据的ajax的类型。可选post,get
			loadonce : true,
			scroll : true,
			height : "600",
			width : "1500",
			viewrecords : true, // 定义是否在导航条上显示总的记录数
			jsonReader : {
				root : 'data', // 注意这里 详细请到官方查看
				/*	total : 'totalPage', // 总页数
					page : 'page', // 页码
					records : 'totalSize', // 总记录数
					,
					repeatitems : false*/
				id : "id"
			},
			caption : "结果汇总" //表格的标题名字
		});
	/*创建jqGrid的操作按钮容器*/
	/*可以控制界面上增删改查的按钮是否显示*/
	jQuery("#list2").jqGrid('navGrid', '#pager2', {
		edit : false,
		add : false,
		del : false
	});
}