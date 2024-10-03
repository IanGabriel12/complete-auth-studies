<script setup lang="ts">
import FormField from "@/components/FormField.vue";
import ImageUpload from "@/components/ImageUpload.vue";
</script>

<template>
  <div class="flex h-full justify-center">
    <form class="mt-16 w-96" @submit.prevent>
      <h1 class="text-center text-red-600 font-bold text-4xl">Create an account</h1>
      <p class="text-center mt-4 text-gray-500">Already have an account? <a class="text-red-500 underline">Sign in</a></p>

      <div class="mt-8">
        <label for="profile" class="text-red-600 font-bold">Profile picture</label>
        <ImageUpload class="mt-2"/>
      </div>
      <FormField class="mt-4" name="fullname" label="Full name" />
      <FormField class="mt-4" type="email" name="email" label="E-mail" />
      <FormField class="mt-4" name="username" label="Username" />
      <FormField class="mt-4" name="password" label="Password" type="password"/>
      <FormField class="mt-4" name="confirm" label="Confirm password" type="password" />

      <button class="w-full bg-red-600 mt-8 text-white rounded-md py-2 hover:bg-red-700">Sign up</button>
    </form>
  </div>
</template>

<style scoped>
</style>
