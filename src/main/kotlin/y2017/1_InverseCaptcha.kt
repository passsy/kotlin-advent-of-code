package y2017

private const val input = "659282248893133858981552542523681828522955561639292843326243684738654451464" +
        "8645288129834834862363847542262953164877694234514375164927616649264122487182321437459" +
        "6468519666497324749253532816998953268248525557471275475271631975445394686323698584132" +
        "3268426983528881773567817398626455458641267836443332762162749693995664528371245326525" +
        "5261565511586373551439198276373843771249563722914847255524452675842558622845416218195" +
        "3744593867856182551298315399845596441853695436628213116861621376721682661524946564488" +
        "2471979139879735932641223572323458553951538535242657983125194391119786299497413373819" +
        "6775618715739412713224837531544346114877971977411275354168752719858889347588136787894" +
        "7984761233358945143424117421111353372864499688792514814497572941673638671199278115135" +
        "2971123953491411929283311162448347246678147595149434851612547414253292385894127956967" +
        "5445694654355314925386833175795464912974865287564866767924677333599828829875283753669" +
        "7831762888997976917137661996417165462848413874557331325196493651131824322384776733752" +
        "3479339459543581692445358551397311954884157712614196277664929432218969537545174374758" +
        "1241922657947182232454611837512564776273929815169367899818698892234618847815155578736" +
        "8752956299172479776587238686414114935517969987918397763357936826435518759473463473446" +
        "9586987456443256695688239542426718755279945835212124814737193894379999515861787139328" +
        "9534789214852747976587432857675156884837634687257363975437535621197887877326295229195" +
        "6632351292133981782825494325994559657599991592472958573664853457595166224278335188374" +
        "5823612372335381744454527164468492529747714929848475385886355135726625993529818432592" +
        "6848958828192317538375317946457985874965434486829387647425222952585293626473351211161" +
        "6842973519327714626656217643928331222365773536692158337217724828637756292446196392346" +
        "3685326793489578389182387784519832666572865932872947245617528522968124497438924823545" +
        "7688922179237895954959228638193933854787917647154837695422429184757725387589969781672" +
        "5965684211912363745637189517384995914545717286419516999816152496353147892512396773932" +
        "51756396"

/**
 * --- Day 1: Inverse Captcha ---

The night before Christmas, one of Santa's Elves calls you in a panic. "The printer's broken! We can't print the Naughty or Nice List!" By the time you make it to sub-basement 17, there are only a few minutes until midnight. "We have a big problem," she says; "there must be almost fifty bugs in this system, but nothing else can print The List. Stand in this square, quick! There's no time to explain; if you can convince them to pay you in stars, you'll be able to--" She pulls a lever and the world goes blurry.

When your eyes can focus again, everything seems a lot more pixelated than before. She must have sent you inside the computer! You check the system clock: 25 milliseconds until midnight. With that much time, you should be able to collect all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day millisecond in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

You're standing in a room with "digitization quarantine" written in LEDs along one wall. The only door is locked, but it includes a small interface. "Restricted Area - Strictly No Digitized Users Allowed."

It goes on to explain that you may only leave by solving a captcha to prove you're not a human. Apparently, you only get one millisecond to solve the captcha: too fast for a normal human, but it feels like hours to you.
 */
fun main(args: Array<String>) {
    // part 1
    solve("Part One", input = input) { input, output ->
        input.forEach { digits: String ->
            output.appendln(captcha1(digits).toString())
        }
    }

    // part 2
    solve("Part Two", input = input) { input, output ->
        input.forEach { digits: String ->
            output.appendln(captcha2(digits).toString())
        }
    }
}

/**
--- Part One ---
The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.

For example:

1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
1111 produces 4 because each digit (all 1) matches the next.
1234 produces 0 because no digit matches the next.
91212129 produces 9 because the only digit that matches the next one is the last digit, 9.
What is the solution to your captcha?
 */
fun captcha1(input: String): Int = captcha(input, { (it + 1) % input.length })

/**
--- Part Two ---

You notice a progress bar that jumps to 50% completion. Apparently, the door isn't yet satisfied, but it did emit a star as encouragement. The instructions change:

Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.

For example:

1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
1221 produces 0, because every comparison is between a 1 and a 2.
123425 produces 4, because both 2s match each other, but no other digit has a match.
123123 produces 12.
12131415 produces 4.
What is the solution to your new captcha?
 */
fun captcha2(input: String): Int {
    require(input.length % 2 == 0) { "only even inputs are valid" }
    return captcha(input, { (it + input.length / 2) % input.length })
}

/**
 * @param validationPosition for each index return the index of the validation item
 */
private fun captcha(digits: String, validationPosition: (index: Int) -> Int): Int {
    val numbers = digits.map { it.toString().toInt() }
    return numbers
            .filterIndexed { i, digit -> digit == numbers[validationPosition(i)] }
            .sum()
}

