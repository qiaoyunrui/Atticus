(ns me.juhezi.local-calculate)

; 计算函数
; done
; 118.89.49.184/juhezi/1.gif
(defn function [x y]
  (let [super_x (* x x)
        super_y (* y y)
        numerator (- (Math/pow (Math/sin (+ super_x super_y)) 2) 0.5)
        denominator (Math/pow (+ 1 (* 0.001 (+ super_x super_y))) 2)]
    (- 0.5 (/ numerator denominator))))


; 本地适应度计算
; 将每个个体带入 function 函数中，得到每个个体的适应度
; 并没有计算生存概率
; complete
(defn calculate [data]
  (map #(conj % (function (nth % 0) (nth % 1))) data))