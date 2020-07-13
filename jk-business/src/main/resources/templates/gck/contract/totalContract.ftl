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
                                <label for="mainprojectid" class="control-label ">主项目:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="mainprojectid"
                                            name="mainprojectid">
                                        <option value="">==请选择==</option>
                                        <#if mainProjects??>
                                            <#list mainProjects as item>
                                                <option value="${item.id}">${item.name}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="projectid" class="control-label ">子项目:</label>
                                <div class="form-group">
                                    <select class="form-control select2" style="width: 100%" id="projectid"
                                            name="projectid">
                                        <option value="">==请选择==</option>
                                        <#-- <#list dicList as item>
                                             <option value="${item.id}">${item.name}</option>
                                         </#list>-->
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
        var datatable;
        $(function () {
            //初始化datatable
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/gck/contract/totalPaymentAndFinanaceByContract",
                    type: "post",
                    data: function (d) {
                        d.mainprojectid = $("#mainprojectid").val();
                        d.projectid = $("#projectid").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: "主项目", data: "mainProjectName"},
                    {title: "子项目", data: "subProjectName"},
                    {title: "合同编号", data: "contractNumber"},
                    {title: "合同名称", data: "contractName"},
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


            //通过主项目id获取子项目
            $("#mainprojectid").change(function (e) {
                var mainProjectId = e.target.value; // 获取选中下拉框的值
                if (mainProjectId == null) {
                    mainProjectId = 0;
                }
                $.ajax({
                    type: "GET",
                    url: "${basePath}/gck/contract/getSubProject",
                    dataType: 'json',
                    data: {"mainProjectId": mainProjectId},
                    success: function (data) {
                        if (data.length > 0) {
                            $("#projectid").empty();
                            $("#projectid").append(" <option value=''>==请选择==</option>");
                            $.each(data, function (index, item) {
                                $("#projectid").append(new Option(item.name, item.id));
                            })
                        } else {
                            $("#projectid").empty();
                            $("#projectid").append(" <option value=''>==请选择==</option>");
                        }
                    }
                });
            });

        });


    </script>
</@footer>
