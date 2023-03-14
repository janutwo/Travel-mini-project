// 피셔-예이츠 셔플(Fisher-Yates shuffle) 적용, 같은 빈도로 나타나도록
const MBTI = [
    "ISTJ", "ISTP", "ISFJ", "ISFP",
    "INTJ", "INTP", "INFJ", "INFP",
    "ESTJ", "ESTP", "ESFJ", "ESFP",
    "ENTJ", "ENTP", "ENFJ", "ENFP"
]

function shuffle(array){
    for (let i = array.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

$(function(){
    const typed = new Typed('.typed-words', {
        strings: shuffle(MBTI),
        typeSpeed: 80,
        backSpeed: 80,
        backDelay: 4000,
        startDelay: 1000,
        loop: true,
        showCursor: true,
        preStringTyped: (arrayPos, self) => {
            arrayPos++;
            $('.slides img').removeClass('active');
            $('.slides img[data-id="' + arrayPos + '"]').addClass('active');
        }

    });
})

