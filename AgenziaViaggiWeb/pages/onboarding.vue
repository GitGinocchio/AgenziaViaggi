<template>
  <div class="min-h-screen flex flex-col bg-zinc-50/50 dark:bg-zinc-950 selection:bg-primary/20">
    <header class="py-6 border-b border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900">
      <UContainer class="flex justify-center">
        <NuxtLink to="/"
          class="flex items-center gap-2 font-black text-xl tracking-tight text-primary cursor-pointer hover:opacity-80 transition-opacity">
          <UIcon name="i-lucide-palmtree" class="w-7 h-7" />
          <span>Agenzia<span class="text-zinc-900 dark:text-white">Viaggi</span></span>
        </NuxtLink>
      </UContainer>
    </header>

    <main class="grow flex items-center justify-center p-4">
      <UCard
        class="w-full max-w-2xl border-none ring-1 ring-zinc-200 dark:ring-zinc-800 shadow-sm rounded-3xl bg-white dark:bg-zinc-900 overflow-hidden">

        <div class="pt-10 pb-6">
          <UStepper v-model="currentStep" :items="steps" class="max-w-xs mx-auto" />
        </div>

        <div class="px-8 pb-12 pt-4 min-h-420px flex flex-col items-center justify-center text-center">
          <Transition name="fade" mode="out-in">

            <div v-if="currentStep === 0" :key="0" class="w-full max-w-md space-y-8">
              <div class="space-y-3">
                <h2 class="text-3xl font-black text-zinc-900 dark:text-white tracking-tight uppercase">Il tuo Nome</h2>
                <p class="text-zinc-500 text-lg">Iniziamo a personalizzare la tua esperienza.</p>
              </div>
              <UInput v-model="form.nome" placeholder="Nome e Cognome" size="xl" icon="i-lucide-user"
                input-class="text-center text-xl py-4 rounded-2xl border-zinc-200 dark:border-zinc-800"
                @keyup.enter="currentStep++" />
            </div>

            <div v-else-if="currentStep === 1" :key="1" class="w-full max-w-md space-y-8">
              <div class="space-y-3">
                <h2 class="text-3xl font-black text-zinc-900 dark:text-white tracking-tight uppercase">Contatti</h2>
                <p class="text-zinc-500 text-lg">Dove possiamo inviarti le tue prenotazioni?</p>
              </div>
              <UInput v-model="form.email" type="email" placeholder="mario@esempio.it" size="xl" icon="i-lucide-mail"
                input-class="text-center text-xl py-4 rounded-2xl border-zinc-200 dark:border-zinc-800"
                @keyup.enter="currentStep++" />
            </div>

            <div v-else-if="currentStep === 2" :key="2" class="w-full space-y-8">
              <div class="space-y-3">
                <h2 class="text-3xl font-black text-zinc-900 dark:text-white tracking-tight uppercase">Preferenze</h2>
                <p class="text-zinc-500 text-lg">Cosa cerchi nella tua prossima vacanza?</p>
              </div>

              <div class="flex flex-wrap justify-center gap-2 min-h-60px">
                <TransitionGroup name="tag">
                  <UBadge v-for="tag in form.preferenze" :key="tag" size="lg" variant="soft"
                    class="rounded-full pl-4 pr-2 py-2 font-bold flex items-center gap-2 border border-primary/20">
                    {{ tag }}
                    <UButton icon="i-lucide-x" variant="ghost" color="neutral" size="xs"
                      class="rounded-full hover:bg-primary/10" @click="removeTag(tag)" />
                  </UBadge>
                </TransitionGroup>
              </div>

              <div class="max-w-md mx-auto flex gap-2">
                <UInput v-model="newInterest" placeholder="Aggiungi interesse..." class="grow"
                  input-class="rounded-xl border-zinc-200 dark:border-zinc-800 px-5" @keyup.enter="addInterest" />
                <UButton icon="i-lucide-plus" color="primary" class="rounded-xl px-4" @click="addInterest" />
              </div>

              <div class="pt-6 border-t border-zinc-100 dark:border-zinc-800 max-w-sm mx-auto">
                <p class="text-[10px] uppercase font-black text-zinc-400 tracking-widest mb-4">Suggeriti</p>
                <div class="flex flex-wrap justify-center gap-2">
                  <UButton v-for="s in suggeriti" :key="s" :label="s" variant="outline" color="neutral" size="xs"
                    class="rounded-full border-zinc-200 dark:border-zinc-700 hover:border-primary hover:text-primary transition-colors"
                    :disabled="form.preferenze.includes(s)" @click="addSuggested(s)" />
                </div>
              </div>
            </div>
          </Transition>
        </div>

        <div class="px-8 pb-10 flex justify-center gap-4">
          <UButton v-if="currentStep > 0" variant="ghost" color="neutral" icon="i-lucide-arrow-left"
            class="px-6 py-3 font-bold uppercase tracking-wider text-xs" @click="currentStep--">
            Indietro
          </UButton>

          <UButton :label="currentStep === 2 ? 'Concludi Setup' : 'Avanti'" color="primary" size="xl"
            class="px-12 font-black rounded-2xl shadow-lg shadow-primary/20" trailing-icon="i-lucide-chevron-right"
            :disabled="!isValid" @click="handleNext" />
        </div>
      </UCard>
    </main>

    <footer class="py-8 text-center text-[10px] uppercase tracking-[0.2em] text-zinc-400">
      Setup Account &bull; Agenzia Viaggi 2026
    </footer>
  </div>
</template>

<script setup lang="ts">
import type { StepperItem } from '@nuxt/ui'

const router = useRouter();

definePageMeta({ layout: false })

onMounted(() => {
  const userAccount = localStorage.getItem('user_account')

  if (userAccount) {
    navigateTo('/')
  }
})

const currentStep = ref(0)
const newInterest = ref('')
const suggeriti = ['Mare', 'Montagna', 'Città d\'arte', 'Avventura', 'Relax', 'Neve']

const steps = ref<StepperItem[]>([
  { title: '' }, { title: '' }, { title: '' }
])

const form = reactive({ nome: '', email: '', preferenze: [] as string[] })

const isValid = computed(() => {
  if (currentStep.value === 0) return form.nome.trim().length > 2
  if (currentStep.value === 1) return form.email.includes('@')
  if (currentStep.value === 2) return form.preferenze.length > 0
  return false
})

const addInterest = () => {
  const v = newInterest.value.trim()
  if (v && !form.preferenze.includes(v)) { form.preferenze.push(v); newInterest.value = ''; }
}
const addSuggested = (s: string) => { if (!form.preferenze.includes(s)) form.preferenze.push(s); }
const removeTag = (tag: string) => { form.preferenze = form.preferenze.filter(t => t !== tag); }

const isSubmitting = ref(false)

const handleNext = async () => {
  if (currentStep.value < 2) {
    currentStep.value++
  } else {
    isSubmitting.value = true
    
    try {
      const payload = {
        nome: form.nome,
        email: form.email,
        preferenze: form.preferenze.join(', ')
      }

      await $fetch('http://127.0.0.1:8080/AgenziaViaggiApi/api/clienti', {
        method: 'POST',
        body: payload
      })

      localStorage.setItem('user_account', JSON.stringify(payload))

      useToast().add({
        title: 'Benvenuto!',
        description: 'Il tuo profilo è stato creato con successo.',
        color: 'primary'
      })

      navigateTo('/')
    } catch (error) {
      console.error('Errore durante il salvataggio:', error)
      
      useToast().add({
        title: 'Errore',
        description: 'Impossibile salvare i dati. Riprova più tardi.',
      })
    } finally {
      isSubmitting.value = false
    }
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.tag-enter-active,
.tag-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.tag-enter-from {
  opacity: 0;
  transform: scale(0.8);
}

.tag-leave-to {
  opacity: 0;
  transform: scale(0.5);
}
</style>