# My-Automessage

## :information_source: Informacje:
Prosty plugin na Auto-Message napisany i przetestowany na wersji Paper 1.20.6

## :sparkles: Głowne cechy:
- :keyboard: Podstawowe komendy:
  `/mam help`
  `/mam version`
  `/mam reload`
- :hammer: Uprawnienia:
  `myautomessage.help`
  `myautomessage.version`
  `myautomessage.reload`
- 📄 Obsługa **PlaceholderAPI**
- 🌈 Obsługa formatowania **MiniMessage**

## :hammer_and_wrench: Podstawowa konfiguracja:
```YAML
auto-message:
  interval-in-ticks: 6000 # 5 minut (20 ticków = 1 sekunda)
  prefix: "&7[&aMy-AutoMessage&7] → "
  messages:
    - "Gracz %player_name% ma teraz %player_level% poziomów!"
    - "Na serwerze jest obecnie %online_players% graczy!"
    - "Twój balans to %vault_eco_balance%!"
    - "<red>Uważaj na Creepery!</red>"
```
