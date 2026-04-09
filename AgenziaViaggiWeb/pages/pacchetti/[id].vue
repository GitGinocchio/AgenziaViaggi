<template>
  <div v-if="pacchetto" class="min-h-screen bg-zinc-50/50 dark:bg-zinc-950 pb-20">
    <div class="relative h-[60vh] min-h-400px w-full overflow-hidden">
      <img 
        :src="pacchetto.immagine || `https://source.unsplash.com/featured/?${pacchetto.titolo},travel`" 
        class="w-full h-full object-cover"
      />
      <div class="absolute inset-0 bg-linear-to-t from-zinc-950 via-zinc-950/20 to-transparent" />
      
      <UContainer class="absolute bottom-0 left-0 right-0 pb-12">
        <div class="max-w-3xl">
          <UButton 
            to="/pacchetti" 
            icon="i-lucide-arrow-left" 
            variant="ghost"
            class="mb-6 -ml-3"
          >
            Torna al catalogo
          </UButton>
          
          <div class="flex flex-wrap gap-2 mb-4">
            <UBadge v-if="pacchetto.isOfferta" color="warning" variant="solid" class="font-black">PROMO ATTIVA</UBadge>
            <UBadge v-for="tag in pacchetto.tags?.split(',')" :key="tag" variant="subtle" class="backdrop-blur-md bg-white/10 text-white border-white/20">
              {{ tag.trim() }}
            </UBadge>
          </div>
          
          <h1 class="text-5xl md:text-7xl font-black text-white tracking-tighter mb-4">
            {{ pacchetto.titolo }}
          </h1>
          
          <div class="flex items-center gap-6 text-zinc-300 font-medium">
            <div class="flex items-center gap-2">
              <UIcon name="i-lucide-map-pin" class="text-primary w-5 h-5" />
              Pacchetto Verificato
            </div>
          </div>
        </div>
      </UContainer>
    </div>

    <UContainer class="py-12">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-12">
        
        <div class="lg:col-span-2 space-y-12">
          <section>
            <h2 class="text-2xl font-black mb-4 uppercase tracking-tight">Informazioni sul viaggio</h2>
            <p class="text-lg text-zinc-600 dark:text-zinc-400 leading-relaxed">
              {{ pacchetto.descrizione }}
            </p>
          </section>

          <section v-if="pacchetto.servizi && pacchetto.servizi.length > 0">
            <div class="flex items-center justify-between mb-8">
              <div>
                <h3 class="text-2xl font-black uppercase tracking-tight">Servizi e Attività</h3>
                <p class="text-zinc-500 text-sm">Personalizza il tuo viaggio con le nostre proposte</p>
              </div>
              <UBadge color="primary" variant="soft" size="lg" class="rounded-full">
                {{ pacchetto.servizi.length }} Disponibili
              </UBadge>
            </div>

            <div class="space-y-4">
              <div 
                v-for="servizio in pacchetto.servizi" 
                :key="servizio.id" 
                class="group flex flex-col md:flex-row gap-6 p-5 bg-white dark:bg-zinc-900 rounded-3xl border border-zinc-200 dark:border-zinc-800 hover:border-primary/50 transition-all hover:shadow-xl dark:hover:shadow-primary/5"
              >
                <div class="w-full md:w-48 h-48 md:h-auto relative shrink-0 overflow-hidden rounded-2xl">
                  <img 
                    :src="servizio.immagine || `https://source.unsplash.com/featured/?${servizio.nome},travel`" 
                    class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
                  />
                  <div class="absolute top-3 left-3 opacity-75">
                    <UBadge size="md" variant="solid" class="text-zinc-900 font-bold uppercase tracking-tighter">
                      {{ servizio.tipo }}
                    </UBadge>
                  </div>
                </div>

                <div class="flex-1 flex flex-col justify-between py-1">
                  <div>
                    <div class="flex justify-between items-start mb-2">
                      <h4 class="text-xl font-bold text-zinc-900 dark:text-white">{{ servizio.nome }}</h4>
                      <div class="text-right">
                        <p v-if="servizio.prezzo > 0" class="text-lg font-black text-primary">+{{ servizio.prezzo }}€</p>
                        <p v-else class="text-xs font-black text-green-500 uppercase tracking-widest">Incluso</p>
                      </div>
                    </div>
                    <p class="text-zinc-500 dark:text-zinc-400 text-sm leading-relaxed line-clamp-3 md:line-clamp-none">
                      {{ servizio.descrizione }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </section>

          <section v-if="pacchetto.isOfferta" class="p-8 bg-primary/5 rounded-3xl border border-primary/10">
            <div class="flex items-center gap-4 mb-4">
              <div class="p-3 bg-primary rounded-2xl text-white">
                <UIcon name="i-lucide-info" class="w-6 h-6" />
              </div>
              <h3 class="text-xl font-black">Nota dell'agenzia</h3>
            </div>
            <p class="text-zinc-600 dark:text-zinc-400">
              Questo pacchetto è soggetto a disponibilità limitata. Ti consigliamo di completare la richiesta di prenotazione per bloccare il prezzo attuale.
            </p>
          </section>
        </div>

        <div class="relative">
          <div class="sticky top-24">
            <UCard class="border-none ring-1 ring-zinc-200 dark:ring-zinc-800 shadow-2xl rounded-3xl overflow-hidden">
              <div class="p-6 space-y-6">
                <div class="flex justify-between items-end">
                  <div>
                    <p class="text-sm text-zinc-500 uppercase font-bold tracking-widest">Prezzo totale</p>
                    <p class="text-4xl font-black text-primary">{{ pacchetto.prezzo }}€</p>
                  </div>
                  <p class="text-xs text-zinc-400 pb-1">Tasse incluse</p>
                </div>

                <div class="space-y-3">
                  <div class="flex items-center justify-between text-sm p-3 bg-zinc-50 dark:bg-zinc-800/50 rounded-xl">
                    <span class="flex items-center gap-2"><UIcon name="i-lucide-users" /> Viaggiatori</span>
                    <span class="font-bold">1 Adulto</span>
                  </div>
                  <div class="flex items-center justify-between text-sm p-3 bg-zinc-50 dark:bg-zinc-800/50 rounded-xl">
                    <span class="flex items-center gap-2"><UIcon name="i-lucide-plane" /> Trasporto</span>
                    <span class="font-bold text-green-500">Incluso</span>
                  </div>
                </div>

                <UButton
                  :loading="isBooking"
                  block 
                  size="xl" 
                  color="primary" 
                  class="rounded-2xl font-black shadow-lg shadow-primary/30"
                  @click="isUserRegistered ? handleBooking() : handleLogin()"
                >
                  {{ isUserRegistered ? 'Prenota ora' : 'Accedi' }}
                </UButton>

                <p class="text-center text-[10px] text-zinc-400 uppercase tracking-tighter">
                  Cancellazione gratuita entro 14 giorni
                </p>
              </div>
            </UCard>
          </div>
        </div>

      </div>
    </UContainer>
  </div>

  <div v-else-if="pending" class="min-h-screen flex items-center justify-center">
    <UIcon name="i-lucide-loader-2" class="w-10 h-10 animate-spin text-primary" />
  </div>
</template>

<script setup lang="ts">
const route = useRoute();
const toast = useToast();

const userData = ref<Cliente | null>(null);

onMounted(() => {
  const storedUser = window.localStorage.getItem('user_account');
  if (storedUser) {
    try {
      userData.value = JSON.parse(storedUser);
    } catch (e) {
      console.error("Errore nel parsing dell'utente", e);
    }
  }
});

const isUserRegistered = computed(() => !!userData.value);

const { data: pacchetto, pending } = await useFetch<Pacchetto>(
  `http://localhost:8080/AgenziaViaggiApi/api/pacchetti/${route.params.id}`
);

const isBooking = ref(false);

async function handleBooking() {
  if (!isUserRegistered.value) {
    toast.add({
      title: 'Accesso richiesto',
      description: "Devi effettuare l'accesso per prenotare questo viaggio.",
      color: 'warning'
    });

    return navigateTo({ 
      path: '/onboarding', 
      query: { redirect: route.fullPath } 
    });
  }

  isBooking.value = true;

  try {
    const payload = {
      idCliente: userData.value?.id,
      idPacchetto: pacchetto.value?.id,
      prezzoPagato: pacchetto.value?.prezzo, 
      stato: "IN ATTESA",
      dataPrenotazione: new Date().toISOString() 
    };

    const response = await $fetch<Prenotazione>('http://localhost:8080/AgenziaViaggiApi/api/prenotazioni', {
      method: 'POST',
      body: payload
    });

    toast.add({
      title: 'Prenotazione Inviata!',
      description: `La tua richiesta per ${pacchetto.value?.titolo} è stata registrata.`,
      color: 'primary'
    });

    await navigateTo(`/prenotazioni/${response.id}`);

  } catch (error) {
    console.error("Errore durante la prenotazione:", error);
    toast.add({
      title: 'Errore',
      description: 'Impossibile completare la prenotazione. Riprova più tardi.',
      color: 'error'
    });
  } finally {
    isBooking.value = false;
  }
}

async function handleLogin() {
  await navigateTo({
    path: '/onboarding',
    query: { redirect: route.fullPath }
  });
}
</script>