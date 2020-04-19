import request from '@/utils/request'
export function fetchList(params) {
return request({
url: '/${moduleName}/${changeClassName}/list',
method: 'get',
params: params
})
}
export function create${className}(data) {
return request({
url: '/${moduleName}/${changeClassName}/create',
method: 'post',
data: data
})
}

export function delete${className}(id) {
return request({
url: '/${moduleName}/${changeClassName}/delete/' + id,
method: 'get',
})
}

export function get${className}(id) {
return request({
url: '/${moduleName}/${changeClassName}/' + id,
method: 'get',
})
}

export function update${className}(id, data) {
return request({
url: '/${moduleName}/${changeClassName}/update/' + id,
method: 'post',
data: data
})
}

