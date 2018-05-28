(ns me.juhezi.reader
  (:require me.juhezi.redis))

; 从数据库读取数据
; generation - 第几代
; size - 读取数据规模的数量
(defn read [name generation size])
