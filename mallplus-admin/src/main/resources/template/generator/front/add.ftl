<template> 
    <${changeClassName}-detail :is-edit='false'>
</${changeClassName}-detail>
</template>
<script>
    import ${className}Detail from './components/detail'

    export default {
        name: 'add${className}',
        components: {${className}Detail}
    }
</script>
<style>
</style>


