<template> 
    <${changeClassName}-detail :is-edit='true'>
</${changeClassName}-detail>
</template>
<script>
    import ${className}Detail from './components/detail'

    export default {
        name: 'update${className}',
        components: {${className}Detail}
    }
</script>
<style>
</style>


