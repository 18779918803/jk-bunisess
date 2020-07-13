<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <!-- 搜索条件区 -->
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group">
                                <label for="partya" class="control-label ">甲方:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="partya" name="partya">
                                        <option value="">==请选择==</option>
                                        <#if partyAs??>
                                            <#list partyAs as item>
                                                <option value="${item.id}">${item.name}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="type" class="control-label ">合同类型:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="type" name="type">
                                        <option value="">==请选择==</option>
                                        <@dic type="contract_type" ; dicList>
                                            <#list dicList as item>
                                                <option value="${item.code}">${item.name}</option>
                                            </#list>
                                        </@dic>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="date" class="control-label ">开始时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="startDate" id="startDate"
                                           readonly
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="date" class="control-label ">结束时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="endDate" id="endDate"
                                           readonly
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="text-center">
                                    <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                        <i class="fa fa-search fa-btn"></i>搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body">
                    <!-- 工具按钮区 -->
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="contract:contract:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/contract/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover"></table>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;
        $(function () {

            // 添加金额格式化
            jQuery.extend({
                formatFloat: function (src, pos) {
                    var num = parseFloat(src).toFixed(pos);
                    num = num.toString().replace(/\$|\,/g, '');
                    if (isNaN(num)) num = "0";
                    sign = (num == (num = Math.abs(num)));
                    num = Math.floor(num * 100 + 0.50000000001);
                    cents = num % 100;
                    num = Math.floor(num / 100).toString();
                    if (cents < 10) cents = "0" + cents;
                    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                        num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
                    return (((sign) ? '' : '-') + num + '.' + cents);
                }
            });
            //初始化datatable
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/gck/contract/totalByPartyA",
                    type: "post",
                    data: function (d) {
                        d.partya = $("#partya").val();
                        d.type = $("#type").val();
                        d.startDate = $("#startDate").val();
                        d.endDate = $("#endDate").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    /*   {
                           "title": "序号",
                           "data": "name",
                           "render": function (data, type, full, meta) {
                               return meta.row + 1 + meta.settings._iDisplayStart;
                           }
                       },*/
                    {title: "公司名称", data: "partyaName"},
                    {
                        title: "审批金额", data: "approvalSum", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "付款金额", data: "paySum", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    }

                ]
            });


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });
        });


    </script>
</@footer>
