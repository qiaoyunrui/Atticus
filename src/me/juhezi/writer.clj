(ns me.juhezi.writer
  (:require [me.juhezi.redis :refer :all]
            [taoensso.carmine :as car :refer (wcar)]))


; 保存数据
; generation - 第几代
; data - 需要保存的数据
(defn write [name generation data]
  (let [key (str name "-" generation)]
    (map #(wcar* (car/lpush key (str (nth % 0 0) "," (nth % 1 0)))) data)
    key))

;(str "key is " key)