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
                                <label for="partya" class="control-label ">贷款单位:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="leaderId" name="leaderId">
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
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/loan/leaderExcelOut"></@krt.excelOut>
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
                    url: "${basePath}/gck/loan/totalLeader",
                    type: "post",
                    data: function (d) {
                        d.leaderId = $("#leaderId").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                   {
                           "title": "序号",
                           "data": "leaderName",
                           "render": function (data, type, full, meta) {
                               return meta.row + 1 + meta.settings._iDisplayStart;
                           }
                   },
                    {title: "贷款方", data: "leaderName"},
                    {
                        title: "贷款金额", data: "amount", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "贷款实收本金", data: "realityAmount", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "贷款实收利息", data: "realityRate", render: function (data) {
                            return jQuery.formatFloat(data, 2);
                        }
                    },
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            return '<@shiro.hasPermission name="Loan:loan:list">'
                                + '<button class="btn btn-xs btn-success searchBtn" rid="' + row.leaderId + '">'
                                + '<i class="fa fa-plus fa-btn"></i>查看'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                        }
                    }


                ]
            });

            //查看
            $(document).on("click", ".searchBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("归还本金和利息", "${basePath}/gck/loan/list?leaderId=" + id)

            });

            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });
        });


    </script>
</@footer>
