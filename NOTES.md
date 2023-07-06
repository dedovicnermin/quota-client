# Without Quota (QuotaProducerSample)

```
Elapsed: 1 s; Rate: current 0 rec/s, average 0 rec/s
Elapsed: 2 s; Rate: current 64 rec/s, average 32 rec/s
Elapsed: 3 s; Rate: current 115 rec/s, average 59 rec/s
Elapsed: 4 s; Rate: current 120 rec/s, average 74 rec/s
Elapsed: 5 s; Rate: current 111 rec/s, average 81 rec/s
Elapsed: 6 s; Rate: current 113 rec/s, average 87 rec/s
Elapsed: 7 s; Rate: current 111 rec/s, average 90 rec/s
Elapsed: 8 s; Rate: current 110 rec/s, average 93 rec/s
Elapsed: 9 s; Rate: current 103 rec/s, average 94 rec/s
Elapsed: 10 s; Rate: current 121 rec/s, average 97 rec/s
Elapsed: 11 s; Rate: current 112 rec/s, average 98 rec/s
Elapsed: 12 s; Rate: current 122 rec/s, average 100 rec/s
Elapsed: 13 s; Rate: current 122 rec/s, average 102 rec/s
Elapsed: 14 s; Rate: current 122 rec/s, average 103 rec/s
Elapsed: 15 s; Rate: current 122 rec/s, average 105 rec/s
Elapsed: 16 s; Rate: current 122 rec/s, average 106 rec/s
Elapsed: 17 s; Rate: current 114 rec/s, average 106 rec/s
Elapsed: 18 s; Rate: current 123 rec/s, average 107 rec/s
Elapsed: 19 s; Rate: current 124 rec/s, average 108 rec/s
Elapsed: 20 s; Rate: current 122 rec/s, average 109 rec/s
Elapsed: 21 s; Rate: current 122 rec/s, average 109 rec/s
Elapsed: 22 s; Rate: current 105 rec/s, average 109 rec/s
Elapsed: 23 s; Rate: current 84 rec/s, average 108 rec/s
Elapsed: 24 s; Rate: current 118 rec/s, average 108 rec/s
Elapsed: 25 s; Rate: current 120 rec/s, average 109 rec/s
Elapsed: 26 s; Rate: current 104 rec/s, average 109 rec/s
Elapsed: 27 s; Rate: current 119 rec/s, average 109 rec/s
Elapsed: 28 s; Rate: current 114 rec/s, average 109 rec/s
Elapsed: 29 s; Rate: current 106 rec/s, average 109 rec/s
Elapsed: 30 s; Rate: current 114 rec/s, average 109 rec/s
Elapsed: 31 s; Rate: current 120 rec/s, average 110 rec/s
Elapsed: 32 s; Rate: current 122 rec/s, average 110 rec/s
Elapsed: 33 s; Rate: current 124 rec/s, average 110 rec/s
Elapsed: 34 s; Rate: current 118 rec/s, average 111 rec/s
Elapsed: 35 s; Rate: current 123 rec/s, average 111 rec/s
Elapsed: 36 s; Rate: current 122 rec/s, average 111 rec/s
Elapsed: 37 s; Rate: current 122 rec/s, average 112 rec/s
Elapsed: 38 s; Rate: current 122 rec/s, average 112 rec/s
Elapsed: 39 s; Rate: current 122 rec/s, average 112 rec/s
Elapsed: 40 s; Rate: current 122 rec/s, average 112 rec/s
Elapsed: 41 s; Rate: current 122 rec/s, average 113 rec/s
Elapsed: 42 s; Rate: current 115 rec/s, average 113 rec/s
Elapsed: 43 s; Rate: current 113 rec/s, average 113 rec/s
Elapsed: 45 s; Rate: current 123 rec/s, average 113 rec/s
Elapsed: 46 s; Rate: current 113 rec/s, average 113 rec/s
Elapsed: 47 s; Rate: current 111 rec/s, average 113 rec/s
Elapsed: 48 s; Rate: current 111 rec/s, average 113 rec/s
Elapsed: 49 s; Rate: current 116 rec/s, average 113 rec/s
Elapsed: 50 s; Rate: current 111 rec/s, average 113 rec/s
Elapsed: 51 s; Rate: current 124 rec/s, average 113 rec/s
Elapsed: 52 s; Rate: current 120 rec/s, average 113 rec/s
```


## with quota

```
./../scripts/quota/producer_network_with_clientid.sh dev gke nermin pump 100000
Completed updating config for user nermin.
```

```
Elapsed: 1 s; Rate: current 1 rec/s, average 1 rec/s
Elapsed: 2 s; Rate: current 113 rec/s, average 50 rec/s
Elapsed: 3 s; Rate: current 10 rec/s, average 38 rec/s
Elapsed: 4 s; Rate: current 10 rec/s, average 32 rec/s
Elapsed: 5 s; Rate: current 10 rec/s, average 28 rec/s
Elapsed: 6 s; Rate: current 10 rec/s, average 25 rec/s
Elapsed: 7 s; Rate: current 10 rec/s, average 23 rec/s
Elapsed: 8 s; Rate: current 10 rec/s, average 21 rec/s
Elapsed: 9 s; Rate: current 10 rec/s, average 20 rec/s
Elapsed: 10 s; Rate: current 10 rec/s, average 19 rec/s
Elapsed: 11 s; Rate: current 10 rec/s, average 18 rec/s
Elapsed: 12 s; Rate: current 10 rec/s, average 18 rec/s
Elapsed: 13 s; Rate: current 10 rec/s, average 17 rec/s
Elapsed: 14 s; Rate: current 10 rec/s, average 16 rec/s
Elapsed: 15 s; Rate: current 10 rec/s, average 16 rec/s
Elapsed: 16 s; Rate: current 10 rec/s, average 16 rec/s
Elapsed: 17 s; Rate: current 10 rec/s, average 15 rec/s
Elapsed: 18 s; Rate: current 10 rec/s, average 15 rec/s
Elapsed: 19 s; Rate: current 10 rec/s, average 15 rec/s
Elapsed: 20 s; Rate: current 10 rec/s, average 15 rec/s
Elapsed: 21 s; Rate: current 10 rec/s, average 14 rec/s
Elapsed: 22 s; Rate: current 10 rec/s, average 14 rec/s
Elapsed: 23 s; Rate: current 10 rec/s, average 14 rec/s
Elapsed: 24 s; Rate: current 10 rec/s, average 14 rec/s
Elapsed: 25 s; Rate: current 10 rec/s, average 14 rec/s
Elapsed: 26 s; Rate: current 10 rec/s, average 13 rec/s
Elapsed: 27 s; Rate: current 9 rec/s, average 13 rec/s
Elapsed: 28 s; Rate: current 10 rec/s, average 13 rec/s
Elapsed: 29 s; Rate: current 10 rec/s, average 13 rec/s
Elapsed: 30 s; Rate: current 10 rec/s, average 13 rec/s
Elapsed: 31 s; Rate: current 10 rec/s, average 13 rec/s
Elapsed: 32 s; Rate: current 10 rec/s, average 13 rec/s
```

java -cp quota-client-jar-with-dependencies.jar io.nermdev.kafka.quota_client.clients.BufferedQuotaProducerSample ../src/main/resources/application.properties
