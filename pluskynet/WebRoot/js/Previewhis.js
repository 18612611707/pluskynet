$(function() {
	//页面加载完成之后执行
	pageInit();
});
function pageInit() {
	var lastsel;
	//创建jqGrid组件
	jQuery("#list2").jqGrid(
		{
			url : '../PreviewhisAction!select.action', //组件创建完成之后请求数据的url
			datatype : "json", //请求数据返回的类型。可选json,xml,txt
			colNames : [ 'id', '规则', '预览时间', '数据总量', '符合数', '不符合数', '创建人账号' ], //jqGrid的列显示名字
			colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
				{
					name : 'id',
					index : 'id',
					width : 55,
					sortable : false
				},
				{
					name : 'sample',
					index : 'sample',
					width : 55,
					sortable : false
				},
				{
					name : 'createtime',
					index : 'createtime',
					width : 100,
					sortable : false
				},
				{
					name : 'sum',
					index : 'sum',
					width : 100,
					sortable : false
				},
				{
					name : 'accord',
					index : 'accord',
					width : 100,
					sortable : false
				},
				{
					name : 'noaccord',
					index : 'noaccord',
					width : 100,
					sortable : false
				},
				{
					name : 'createname',
					index : 'createname',
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
			caption : "预览历史" //表格的标题名字
		});
	/*创建jqGrid的操作按钮容器*/
	/*可以控制界面上增删改查的按钮是否显示*/
	jQuery("#list2").jqGrid('navGrid', '#pager2', {
		edit : false,
		add : false,
		del : false
	});
}