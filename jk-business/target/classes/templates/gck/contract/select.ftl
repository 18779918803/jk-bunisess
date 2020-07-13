<#include "/common/layoutList.ftl">
<@header></@header>
<@body>
    <section class="content">
        <!-- 参数 -->
        <input type="hidden" id="sId" value="${RequestParameters['sId']!}" >
        <input type="hidden" id="scontractname" value="${RequestParameters['scontractname']!}">
        <input type="hidden" id="snumber" value="${RequestParameters['snumber']!}">
        <input type="hidden" id="smainProjectName" value="${RequestParameters['smainProjectName']!}" >
        <input type="hidden" id="ssubProjectName" value="${RequestParameters['ssubProjectName']!}" >
        <input type="hidden" id="spartyaName" value="${RequestParameters['spartyaName']!}" >
        <input type="hidden" id="spartybName" value="${RequestParameters['spartybName']!}" >

        <!-- 搜索条件区 -->
        <div class="box-search">
            <div class="row row-search">
                <div class="col-xs-12">
                    <form class="form-inline" action="">
                        <div class="form-group">
                            <label for="number" class="control-label ">合同编号:</label>
                            <input type="text" id="number" name="number" placeholder="合同编号" class="form-control">
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
                            <label for="state" class="control-label ">合同状态:</label>
                            <div class="form-group">
                                <select class="form-control select2" style="width: 100%" id="state" name="state">
                                    <option value="">==请选择==</option>
                                    <@dic type="contract_state" ; dicList>
                                        <#list dicList as item>
                                            <option value="${item.code}">${item.name}</option>
                                        </#list>
                                    </@dic>
                                </select>
                            </div>
                        </div>

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
                            <label for="partyb" class="control-label ">乙方:</label>
                            <div class="form-group">
                                <select class="form-control select2" style="width: 100%" id="partyb" name="partyb">
                                    <option value="">==请选择==</option>
                                    <#if partyBs??>
                                        <#list partyBs as item>
                                            <option value="${item.id}">${item.name}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status" class="control-label ">审批状态:</label>
                            <div class="form-group">
                                <select class="form-control select2" style="width: 100%" id="status" name="status">
                                    <option value="">==请选择==</option>
                                    <@dic type="approval_state" ; dicList>
                                        <#list dicList as item>
                                            <option value="${item.code}">${item.name}</option>
                                        </#list>
                                    </@dic>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="contractname" class="control-label ">合同名称:</label>
                            <input type="text" id="contractname" name="contractname" placeholder="合同名称"
                                   class="form-control">
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
        <div class="box-krt">
            <div class="box-body">
                <div class="table-responsive">
                    <table id="datatable" class="table table-bordered table-hover table-krt">
                    </table>
                </div>
            </div>
        </div>
    </section>
</@body>
<@footer>
    <!-- ./wrapper -->
    <!-- page script -->
    <script type="text/javascript">
        var datatable;

        function initTable() {

            //初始化datatable
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/gck/contract/list",
                    type: "post",
                    data: function (d) {
                        d.number = $("#number").val();
                        d.type = $("#type").val();
                        d.state = $("#state").val();
                        d.projectid = $("#projectid").val();
                        d.partya = $("#partya").val();
                        d.partyb = $("#partyb").val();
                        d.status = $("#status").val();
                        d.mainprojectid = $("#mainprojectid").val();
                        d.contractname = $("#contractname").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {
                        "title": "序号",
                        "data": "name",
                        "render": function (data, type, full, meta) {
                            return meta.row + 1 + meta.settings._iDisplayStart;
                        }
                    },
                    {title: "合同编号", data: "number"},
                    {
                        title: "合同类型", data: "type",
                        render: function (data) {
                            return krt.util.getDic('contract_type', data);
                        }
                    },
                    {title: "中标价", data: "price"},
                    {
                        title: "合同状态", data: "state",
                        render: function (data) {
                            return krt.util.getDic('contract_state', data);
                        }
                    },
                    /*  {title: "审批金额", data: "approvalsum"},
                      {title: "付款金额", data: "paysum"},
                      {title: "发票金额", data: "billsum"},*/
                    {title: "子项目", data: "subProjectName"},
                    {title: "甲方", data: "partyaName"},
                    {title: "乙方", data: "partybName"},
                    /*    {title: "审批状态", data: "status"},*/
                    {title: "主项目", data: "mainProjectName"},
                    {title: "合同名称", data: "contractname"},
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            return '<input type="radio"  name="check" class="icheck check" value="' + data + '"  contractname="' + row.contractname + '"  mainProjectName="' + row.mainProjectName + '" subProjectName="' + row.subProjectName + '" number="' + row.number + '" partyaName="' + row.partyaName+
                            '" partybName="' + row.partybName+ '">';
                        }
                    }
                ]
            });

        }

        $(function () {
            //初始化datatable
            initTable();
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

        $(document).on("click", ".icheck", function () {
            //alert(event.type + ' callback');
            var sid = $(this).val();
            var snumber = $(this).attr("number");
            var scontractname = $(this).attr("contractname");
            var smainProjectName = $(this).attr("mainProjectName");
            var ssubProjectName = $(this).attr("subProjectName");
            var spartyaName = $(this).attr("partyaName");
            var spartybName = $(this).attr("partybName");
            $("#sId").val(sid);
            $("#snumber").val(snumber);
            $("#scontractname").val(scontractname);
            $("#smainProjectName").val(smainProjectName);
            $("#ssubProjectName").val(ssubProjectName);
            $("#spartyaName").val(spartyaName);
            $("#spartybName").val(spartybName);
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });


    </script>
</@footer>