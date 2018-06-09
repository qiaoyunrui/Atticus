(ns me.juhezi.local-calculate)

(def interval_start -10.0)
(def interval_end 10.0)

; 计算函数
; done
; 118.89.49.184/juhezi/1.gif
(defn function [x y]
  (let [super_x (* x x)
        super_y (* y y)
        numerator (- (Math/pow (Math/sin (+ super_x super_y)) 2) 0.5)
        denominator (Math/pow (+ 1 (* 0.001 (+ super_x super_y))) 2)]
    (- 0.5 (/ numerator denominator))))

; 将一个染色体 chromosome 映射为区间 interval 之间的数值
(defn decode [chromosome chromosome_size]
  (let [d (- interval_end interval_start)
        n (- (Math/pow 2 chromosome_size) 1)]
    (+ interval_start (/ (* d chromosome) n))))


;def decode(self, interval, chromosome):
;d = interval[1] - interval[0]
;n = float(2 ** self.chromosome_size - 1)
;return interval[0] + chromosome * d / n

; 本地适应度计算
; 将每个个体带入 function 函数中，得到每个个体的适应度
; 并没有计算生存概率
; complete
(defn calculate [data chromosome_size]
  (map #(conj % (function (decode (nth % 0) chromosome_size) (decode (nth % 1) chromosome_size))) data))