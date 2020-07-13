<#include "/common/layoutList.ftl">
<@header></@header>
<@body>
    <section class="content">
        <!-- 参数 -->
        <input type="hidden" id="paymentId" value="${paymentId!}" >
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
                    url: "${basePath}/gck/payment/record",
                    type: "post",
                    data: function (d) {
                        d.type='05';
                        d.approvalId=$("#paymentId").val();
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
                    {title: "审批人", data: "name"},
                    {
                        title: "审批状态", data: "approvalStatus",
                        render: function (data) {
                            return krt.util.getDic('approval_state', data);
                        }
                    },
                    {title: "审批描述", data: "approvalDes"},
                    {title: "审批时间", data: "approvalTime"},
                ]
            });
        }
        $(function () {
            //初始化datatable
            initTable();
        });



    </script>
</@footer>