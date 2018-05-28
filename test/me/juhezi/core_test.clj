(ns me.juhezi.core-test
  (:require [clojure.test :refer :all]
            [me.juhezi.core :refer :all]
            [taoensso.carmine :as car :refer (wcar)]
            [me.juhezi.redis :refer :all]))


(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest b-test
  (testing "测试失败"
    (is (wcar* (car/ping)
               (car/set "foo" "bar")
               (car/get "foo"))
        "bar")))