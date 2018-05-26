(ns me.juhezi.core)

; 计算个体生存概率
; 个体适应度 / 所有个体适应度之和
; complete
(defn calculate_survival_probability [data]
  (let [total_fitness (reduce (fn [m v] (+ m (nth v 2))) 0 data)]
    (map #(conj []
                (nth % 0 0)
                (nth % 1 0)
                (/ (nth % 2 0) total_fitness)) data)))

; 对染色体进行选择
; 基于轮盘赌博机
; 生成一个随机数，生存概率大于这个随机数的，就继续生存下去，否则就毁灭
; complete
(defn select
  [data]
  (let [probability (rand 1)]
    ; (println "概率为：" probability)
    (filter #(<= probability (nth % 2 0)) data)))

; 染色体交叉
; 将两个染色体进行交叉配对，从而生成两个新的染色体
; chromosome_size 染色体数量
; crossover_probability 交叉概率
; t 第几位染色体进行交叉
; complete
(defn cross [chromosome1 chromosome2 chromosome_size crossover_probability]
  (let [probability (rand 1)
        n (- (int (Math/pow 2 chromosome_size)) 1)]
    (if (and (= chromosome1 chromosome2) (< probability crossover_probability))
      (list chromosome1 chromosome2)                        ; 不满足交叉的条件
      (let [t (rand-int chromosome_size)
            mask (bit-shift-left n t)
            r1 (bit-and chromosome1 mask)
            r2 (bit-and chromosome2 mask)
            r_mask (bit-shift-right n (- chromosome_size t))
            l1 (bit-and chromosome1 r_mask)
            l2 (bit-and chromosome2 r_mask)]
        (list [(+ r1 l2) (+ r2 l1)])))))



; 染色体变异模拟
; mutation_probability 变异概率
; complete
(defn mutate [chromosome chromosome_size mutation_probability]
  (let [probability (rand 1)]
    (if (> probability mutation_probability)
      chromosome
      (let [t (rand-int chromosome_size)
            mask1 (bit-shift-left 1 t)
            mask2 (bit-and chromosome mask1)]
        (println "t" t)
        (if (> mask2 0)
          (bit-and chromosome (bit-not mask2))
          (bit-or chromosome mask1))))))