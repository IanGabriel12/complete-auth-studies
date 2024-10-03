<script setup lang="ts">
const props = defineProps({
    label: {
        required: true,
        type: String
    },
    name: {
        required: true,
        type: String
    },
    type: {
        default: "text"
    }
})
</script>

<template>
    <div class="field">
        <label :for="props.name" class="block text-red-600 text-md font-semibold">{{ props.label }}</label>
        <input :name="props.name" :type="props.type" class="p-1 border-red-200 border-2 rounded-md mt-1 w-full shadow-md">
    </div>
</template>

<style>

</style>