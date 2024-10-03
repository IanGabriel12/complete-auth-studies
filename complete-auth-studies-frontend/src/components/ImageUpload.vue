<script setup lang="ts">
import { useTemplateRef } from 'vue';


const fileInput = useTemplateRef('file-input');
const imageContainer = useTemplateRef('image-container');

function clickFileInput(event: MouseEvent) {
    fileInput.value?.click();
}

function changeImageContainerBackground(event: Event) {
    const files = fileInput.value?.files;
    if(!FileReader) {
        throw new Error('Browser not supported');
    }

    if(!files || !files.length) {
        throw new Error('Any files were uploaded');
    }

    const fileReader = new FileReader();
    fileReader.onload = () => {
        if(imageContainer.value) {
            imageContainer.value.style.backgroundColor = '';
            console.log(fileReader.result?.toString() || '');
            imageContainer.value.src = fileReader.result?.toString() || '';
        }
    }
    fileReader.readAsDataURL(files[0]);
    
}
</script>

<template>
    <div class="flex gap-3 h-20 w-full">
        <div class="rounded-md w-20 h-full bg-slate-500">
            <img ref="image-container" class="w-full h-full rounded-md border-0">
        </div>
        <button @click="clickFileInput"class="h-full bg-red-600 flex-1 text-white font-bold hover:bg-red-700 rounded-md">
            Upload Image
        </button>
        <input @change="changeImageContainerBackground" ref="file-input" type="file" class="hidden">
    </div>
    
</template>

<style scoped>

</style>