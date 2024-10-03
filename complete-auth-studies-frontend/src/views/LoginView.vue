<script setup lang="ts">
import FormField from "@/components/FormField.vue";
</script>

<template>
  <div class="flex h-full">
    <div class="bg-red-600 w-4/6 h-f"></div>

    <main id="login" class="flex mt-60 justify-center w-2/6">
      
      <form @submit.prevent class="w-96">
        <h1 class="text-red-600 font-bold text-5xl">Welcome back</h1>
        <span class="text-sm text-gray-500">Do not have an account? <a class="text-red-500 underline">Create one here.</a></span>
        
        <FormField class="mt-8" name="username" label="Username"/>

        <div>
          <FormField class="mt-4" type="password" name="password" label="Password"/>
          <a class="text-sm text-red-500 underline block mt-1">Forgot my password</a>
        </div>

        <button class="mt-6 w-full bg-red-600 p-2 text-white rounded-md">Login</button>
      </form>
      
    </main>
  </div>
</template>

<style scoped>
</style>
