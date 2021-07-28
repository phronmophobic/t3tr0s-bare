(ns game.paint
  (:require
   [membrane.ui :as ui]
   [game.board :refer [empty-board
                       read-board
                       board-size
                       piece-type-adj]])
  (:import java.net.URL))

;;------------------------------------------------------------
;; TILEMAP
;;------------------------------------------------------------

(def tilemap-gameboy-real
  (url->image "https://github.com/phronmophobic/t3tr0s-bare/raw/master/resources/public/tilemap-gameboy-real.png"))
(def tilemap-gameboy-real-adj
  (url->image "https://github.com/phronmophobic/t3tr0s-bare/raw/master/resources/public/tilemap-gameboy-real-adj.png"))

; The size of a cell in pixels.
(def cell-size 32)

(def value-position
  "An ordering imposed on the possible cell types, used for tilemap position."
  { 0 0
   :I 1
   :L 2
   :J 3
   :S 4
   :Z 5
   :O 6
   :T 7
   :G 8  ; ghost piece
   :H 9  ; highlighted (filled or about to collapse)
   })

(defn get-image-region
  "Get the tilemap and position for the image of the given cell value."
  [value]
  (let [string-value (str value)]
    (if-not (= (subs string-value 0 1) "I")
      (let [[k a] (piece-type-adj value)
            row 0
            col (value-position k)
            size 40]
        [tilemap-gameboy-real row col size])
      (let [[k a] (piece-type-adj value)
            row 0
            col a
            size 40]
        [tilemap-gameboy-real-adj row col size]))))

;;------------------------------------------------------------
;; DRAWING
;;------------------------------------------------------------

(defn draw-board
  "Draw the given board to the canvas."
  ([board scale] (draw-board board scale 0))
  ([board scale y-cutoff]
   (let [[w h] (board-size board)]
     (vec
      (for [x (range w) y (range h)]
        (let [                          ; tilemap region
              [img row col size] (get-image-region (read-board x y board))
              size (or size cell-size)

                                        ; source coordinates (on tilemap)
              sx (* size col) ; Cell-size is based on tilemap, always extract with that size
              sy (* size row)
              sw size
              sh size

                                        ; destination coordinates (on canvas)
              dx (* scale x)
              dy  (* scale (- y y-cutoff))
              dw scale
              dh scale]
          (ui/translate
             (- dx sx) (- dy sy)
             (ui/scissor-view [sx sy] [sw sh]
                              img))))))))
