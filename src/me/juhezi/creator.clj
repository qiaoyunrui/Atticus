(ns me.juhezi.creator)

; 生成随机数据，即生成个体数据
; 每个个体只有两个数据域，即 x 和 y
; 在本项目中，染色体长度为 25 位

; chromosomeSize 染色体的个数
; size 初代个体数
; complete
(defn create [chromosome_size size]
  (let [x (int (Math/pow 2 chromosome_size))]
    (repeatedly size #(conj [] (rand-int x) (rand-int x)))))
