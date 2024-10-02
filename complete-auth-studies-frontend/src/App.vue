<script setup lang="ts">
</script>

<template>
  <div class="flex h-full">
    <div class="bg-red-600 w-4/6 h-f"></div>

    <main id="login" class="flex mt-60 justify-center w-2/6">
      
      <form @submit.prevent class="w-96">
        <h1 class="text-red-600 font-bold text-5xl">Welcome back</h1>
        <span class="text-sm text-gray-500">Do not have an account? <a class="text-red-500 underline">Create one here.</a></span>
        
        <div class="field mt-8">
          <label for="username" class="block text-red-600 text-md font-semibold">Username</label>
          <input type="text" name="username" class="p-1 border-red-200 border-2 rounded-md mt-1 w-full shadow-md">
        </div>

        <div class="field mt-4">
          <label for="password" class="text-red-600 text-md font-semibold">Password</label>
          <input type="password" name="password" id="password" class="p-1 border-red-200 border-2 rounded-md mt-1 w-full shadow-md">
          <a class="text-sm text-red-500 underline block mt-1">Forgot my password</a>
        </div>

        <button class="mt-6 w-full bg-red-600 p-2 text-white rounded-md">Login</button>
      </form>
      
    </main>
  </div>
</template>

<style scoped>
</style>
