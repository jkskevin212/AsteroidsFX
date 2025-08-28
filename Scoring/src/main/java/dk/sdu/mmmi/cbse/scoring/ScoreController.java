package dk.sdu.mmmi.cbse.scoring;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/score")
public class ScoreController {
    private final AtomicInteger score = new AtomicInteger();

    @GetMapping
    public int get() { return score.get(); }

    @PostMapping("/add")
    public int add(@RequestParam int points) {
        return score.addAndGet(points);
    }

    @PostMapping("/reset")
    public void reset() {
        score.set(0);
    }


}
