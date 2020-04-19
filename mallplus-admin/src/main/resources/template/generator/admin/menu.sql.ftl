-- 菜单对应按钮SQL

insert into `sys_permission` ( `name`, `pid`, `status`, `sort`, `value`, `type`, `create_time`, `uri`, `icon`) values
( '${prefix}列表', '47', '1', '0', '${moduleName}:${changeClassName}:read', '1', '2018-09-29 16:18:51', '${changeClassName}', null);
insert into `sys_permission` ( `name`, `pid`, `status`, `sort`, `value`, `type`, `create_time`, `uri`, `icon`) values
( '添加${prefix}', '47', '1', '0', '${moduleName}:${changeClassName}:create', '2', '2018-09-29 16:18:51', 'add${className}', null);
insert into `sys_permission` ( `name`, `pid`, `status`, `sort`, `value`, `type`, `create_time`, `uri`, `icon`) values
( '修改${prefix}', '47', '1', '0', '${moduleName}:${changeClassName}:update', '2', '2018-09-29 16:18:51', 'update${className}', null);
insert into `sys_permission` ( `name`, `pid`, `status`, `sort`, `value`, `type`, `create_time`, `uri`, `icon`) values
( '删除${prefix}', '47', '1', '0', '${moduleName}:${changeClassName}:delete', '2', '2018-09-29 16:18:51', 'delete${className}', null);


{
path: '${changeClassName}',
name: '${changeClassName}',
component: () => import('@/views/${moduleName}/${changeClassName}/index'),
meta: {title: '${prefix}列表', icon: 'product-list'}
},

{
path: 'add${className}',
name: 'add${className}',
component: () => import('@/views/${moduleName}/${changeClassName}/add'),
meta: {title: '添加${prefix}'},
hidden: true
},
{
path: 'update${className}',
name: 'update${className}',
component: () => import('@/views/${moduleName}/${changeClassName}/update'),
meta: {title: '编辑${prefix}'},
hidden: true
},
