(ns me.juhezi.core)

(def size 50)
(def chromosome_size 25)
(def crossover_probability 0.8)
(def mutation_probability 0.9)

; 计算个体生存概率
; 个体适应度 / 所有个体适应度之和
; complete
(defn calculate_survival_probability [data]
  (let [total_fitness (reduce (fn [m v] (+ m (nth v 2))) 0 data)]
    (map #(conj []
                (nth % 0 0)
                (nth % 1 0)
                (/ (nth % 2 0) total_fitness)) data)))

; 轮盘赌算法
; 并不是很清楚
; waiting to study
(defn wrand
  "given a vector of slice sizes, returns the index of a slice given a
  random spin of a roulette wheel with compartments proportional to
  slices."
  [slices]
  (let [total (reduce + slices)
        r (rand total)]
    (loop [i 0 sum 0]
      (if (< r (+ (nth slices i) sum))
        i
        (recur (inc i) (+ (nth slices i) sum))))))

; 对染色体进行选择
; 基于轮盘赌博机
; 有问题，不应该这么写
; complete
(defn select
  [data]
  (wrand (map #(nth % 2 0) data)))

; 染色体交叉
; 将两个染色体进行交叉配对，从而生成两个新的染色体
; chromosome_size 染色体数量
; crossover_probability 交叉概率
; t 第几位染色体进行交叉
; complete
(defn cross [chromosome1 chromosome2 chromosome_size crossover_probability]
  (let [probability (rand 1)
        n (- (int (Math/pow 2 chromosome_size)) 1)]
    (if (or (= chromosome1 chromosome2) (> probability crossover_probability)) ;两条染色体一样 或者 p < 变异概率
      (list chromosome1 chromosome2)                        ; 不满足交叉的条件
      (let [t (rand-int chromosome_size)
            mask (bit-shift-left n t)
            r1 (bit-and chromosome1 mask)
            r2 (bit-and chromosome2 mask)
            r_mask (bit-shift-right n (- chromosome_size t))
            l1 (bit-and chromosome1 r_mask)
            l2 (bit-and chromosome2 r_mask)]
        (list (+ r1 l2) (+ r2 l1))))))



; 染色体变异模拟
; mutation_probability 变异概率
; 检查过了，没有问题
; complete
(defn mutate [chromosome chromosome_size mutation_probability]
  (let [probability (rand 1)]
    (if (> probability mutation_probability)
      chromosome
      (let [t (rand-int chromosome_size)
            mask1 (bit-shift-left 1 t)
            mask2 (bit-and chromosome mask1)]
        (if (> mask2 0)
          (bit-and chromosome (bit-not mask2))
          (bit-or chromosome mask1))))))

; 种群的整个进化过程
; 单个进化
; （对个体计算适应度）
; 计算个体的选择概率
(defn single_evolve [data chromosome_size crossover_probability mutation_probability]
  (let [index1 (select data)
        index2 (select data)
        individual1 (nth data index1)                       ;选择
        individual2 (nth data index2)
        individual1_x (nth individual1 0)                   ;交叉
        individual1_y (nth individual1 1)
        individual2_x (nth individual2 0)
        individual2_y (nth individual2 1)
        new_chromosome_x (cross individual1_x individual2_x chromosome_size crossover_probability) ;x1 x2
        new_chromosome_y (cross individual1_y individual2_y chromosome_size crossover_probability)
        new_individual1 (conj [] (mutate (nth new_chromosome_x 0 0) chromosome_size mutation_probability) ;变异
                              (mutate (nth new_chromosome_y 0 0) chromosome_size mutation_probability))
        new_individual2 (conj [] (mutate (nth new_chromosome_x 1) chromosome_size mutation_probability)
                              (mutate (nth new_chromosome_y 1) chromosome_size mutation_probability))]
    (map-indexed (fn [idx itm]
                   (if (= idx index1)
                     new_individual1
                     (if (= idx index2)
                       new_individual2
                       itm))) data)))

(defn evolve [data size chromosome_size crossover_probability mutation_probability]
  (reduce (fn [m v]
            (single_evolve m chromosome_size crossover_probability mutation_probability))
          data
          (range (/ size 2))))

(defn main []
  (evolve (me.juhezi.local-calculate/calculate
            (me.juhezi.creator/create chromosome_size size))
          size chromosome_size crossover_probability mutation_probability))