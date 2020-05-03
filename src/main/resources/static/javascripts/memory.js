const cards = document.querySelectorAll('.memory-card');

let hasFlippedCard = false;
let lockBoard = false;
let firstCard, secondCard;

function flipCard() {
  if (lockBoard) return;
  if (this === firstCard) return;

  this.getElementsByClassName('card-back')[0].style.visibility = "hidden";
  this.getElementsByClassName('card-front')[0].style.visibility = "visible";

  if (!hasFlippedCard) {
    hasFlippedCard = true;
    firstCard = this;

    return;
  }

  secondCard = this;

  checkForMatch();
}

function checkForMatch() {
  let isMatch = firstCard.dataset.value === secondCard.dataset.value;

  isMatch ? disableCards() : unflipCards();
}

function disableCards() {
  firstCard.removeEventListener('click', flipCard);
  secondCard.removeEventListener('click', flipCard);

  resetBoard();
}

function unflipCards() {
  lockBoard = true;

	setTimeout(function (d) {
		firstCard.getElementsByClassName('card-back')[0].style.visibility = "visible";
		firstCard.getElementsByClassName('card-front')[0].style.visibility = "hidden";
		
		secondCard.getElementsByClassName('card-back')[0].style.visibility = "visible";
		secondCard.getElementsByClassName('card-front')[0].style.visibility = "hidden";

		resetBoard();
	}, 1500);
}

function resetBoard() {
  hasFlippedCard = false;
  lockBoard = false;
  firstCard=null;
  secondCard = null;
}

(function shuffle() {
	for (var i = 0; i < cards.length; i++) {
		let randomPos = Math.floor(Math.random() * 12);
		cards[i].style.order = randomPos;
		cards[i].addEventListener('click', flipCard)
	}
})();