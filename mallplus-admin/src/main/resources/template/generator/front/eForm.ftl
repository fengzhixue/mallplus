<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="${changeClassName}" :rules="rules" ref="${className}From" label-width="150px">

            <#if columns??>
                <#list columns as column>

                    <el-form-item
                            label="<#if column.columnComment != ''>${column.columnComment}<#else>${column.changeColumnName}</#if>"
                            prop="${column.changeColumnName}">
                        <el-input v-model="${changeClassName}.${column.changeColumnName}" style="width: 370px;"/>
                    </el-form-item>

                </#list>
            </#if>

            <el-form-item>
                <el-button type="primary" @click="onSubmit('${className}From')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('${className}From')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import {create${className}, get${className}, update${className}} from '@/api/${moduleName}/${changeClassName}'
    import SingleUpload from '@/components/Upload/singleUpload'

    const default${className} = {
        name: ''
    };
    export default {
        name: '${className}Detail',
        components: {SingleUpload},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            ${changeClassName}:
            Object.assign({},
        default${className}),
            rules: {
                name: [
                    {required: true, message: '请输入品牌名称', trigger: 'blur'},
                    {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                ],
                    logo
            :
                [
                    {required: true, message: '请输入品牌logo', trigger: 'blur'}
                ],
                    sort
            :
                [
                    {type: 'number', message: '排序必须为数字'}
                ],
            }
        }
        },
        created() {
            if (this.isEdit) {
                get${className}(this.$route.query.id).then(response => {
                    this.${changeClassName} = response.data;
            })
                ;
            } else {
                this.${changeClassName} = Object.assign({},
            default${className})
                ;
            }
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if(valid) {
                        this.$confirm('是否提交数据', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            if(this.isEdit
                    )
                        {
                            update${className}(this.$route.query.id, this.${changeClassName}).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.$message({
                                    message: '修改成功',
                                    type: 'success',
                                    duration: 1000
                                });
                                this.$router.back();
                            }
                        else
                            {
                                this.$message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1000
                                });
                            }

                        })
                            ;
                        }
                    else
                        {
                            create${className}(this.${changeClassName}).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.${changeClassName} = Object.assign({},
                            default${className})
                                ;
                                this.$message({
                                    message: '提交成功',
                                    type: 'success',
                                    duration: 1000
                                });
                                this.$router.back();
                            }
                        else
                            {
                                this.$message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1000
                                });
                            }

                        })
                            ;
                        }
                    })
                        ;

                    } else {
                        this.$message({
                            message: '验证失败',
                            type: 'error',
                            duration: 1000
                        });
                return false;
            }
            })
                ;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.${changeClassName} = Object.assign({},
            default${className})
                ;
            }
        }
    }
</script>
<style>
</style>


