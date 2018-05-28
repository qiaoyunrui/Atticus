(ns me.juhezi.redis
  (:require [taoensso.carmine :as car :refer (wcar)]))

; redis 连接参数
(def server-connect {:pool {} :spec {:host "127.0.0.1" :port 6379}})

; 定义使用宏
(defmacro wcar* [& body] `(car/wcar server-connect ~@body))

