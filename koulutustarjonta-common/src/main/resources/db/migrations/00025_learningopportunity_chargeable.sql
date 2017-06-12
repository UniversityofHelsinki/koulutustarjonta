-- adding "maksullisuus" (chargeable) text column in "koulutus" (learning opportunity)
ALTER TABLE KOULUTUS
  ADD (
  MAKSULLISUUS_FI CLOB,
  MAKSULLISUUS_SV CLOB,
  MAKSULLISUUS_EN CLOB
  )
