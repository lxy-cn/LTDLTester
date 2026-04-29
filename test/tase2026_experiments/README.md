# Readme for TASE 2026

The following files are for the experimental comparison between our LTDLTester-based LTDL model checking method and MCMAS_LDLK.

1. README.md -- this file 
2. LTDLTester.jar -- the executable jar file of LTDLTester 
3. ltdl2smv.txt -- the LTDL formulas for the two examples in the submitted paper of tase 2026
4. test_our_method -- the directory for testing our method
   1. NuSMV -- the linux version of the executable file of NuSMV
   2. ltl2smv -- the linux version of the executable file for translating LTL into smv code
   3. bit_transmission_protocol_ldl_n[i].smv -- the smv code that includes the model of the bit transmission protocol, the smv code for the tester and the output assertion `bit0 -> X1` for the LTDL formula `bit0 -> (<(bit0^i)*>recack)` 
   4. go_back_n_4bits_n[i].smv -- the smv code that includes the model of the Go-Back-N ARQ protocol, the smv code for the tester and the output assertion `!X1 -> W1` for the LTDL formula `([(((envworks ; envbroken) + (envbroken ; envworks))^i)*]TRUE) -> F senderDone`
5. test_mcmas_ldlk -- the directory for testing MCMAS_LDLK
   1. mcmas-ldlk_64 -- the linux version of the executable file of MCMAS_LDLK 
   2. bit_transmission_protocol_ldl.ispl -- the ISPL code that includes the model of the bit transmission protocol, and the LDL formulas `bit0 -> (<(bit0^i)*>recack)` 
   3. go_back_n_4bits.ispl -- the ISPL code that includes the model of the Go-Back-N ARQ protocol, and the LDL formulas `([(((envworks ; envbroken) + (envbroken ; envworks))^n)*]TRUE) -> F senderDone` 
