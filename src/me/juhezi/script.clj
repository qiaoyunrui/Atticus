(ns me.juhezi.script)

; 去掉论文里的空格、tab、换行等等..
(let [out (clojure.java.io/file "/Users/shixi_yunrui/Desktop/result.txt")]
  (spit out (apply str (filter #(and (not= % \r)
                                     (not= % \return)
                                     (not= % \newline)
                                     (not= % \space)
                                     (not= % \tab)
                                     (not= % \t))
                               (seq (slurp "/Users/shixi_yunrui/Desktop/paper.txt"))))))
