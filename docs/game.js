'use strict';

const BASE_WIDTH = 1920;
const BASE_HEIGHT = 1080;
const ASSET_DIR = 'assets/';

const stage = document.getElementById('stage');

const LEVEL_RULES = {
  1: { required: 5, time: 90 },
  2: { required: 10, time: 180 },
  3: { required: 20, time: 175 },
  4: { required: 30, time: 300 },
  5: { required: 67, time: 450 }
};

const ELEMENTS = {
  heart: { name: 'Heart Berry', image: 'HeartBerry.png' },
  star: { name: 'Star Dust', image: 'StarDust.png' },
  spark: { name: 'Spark Ember', image: 'SparkEmber.png' },
  dew: { name: 'Dew Drop', image: 'DewDrop.png' }
};

const POTIONS = {
  dream: {
    name: 'Dream Mist Potion',
    image: 'DreamMist.png',
    recipe: ['star', 'dew'],
    power: 20
  },
  energy: {
    name: 'Energy Splash Potion',
    image: 'EnergySplash.png',
    recipe: ['spark', 'dew'],
    power: 20
  },
  nova: {
    name: 'Nova Spark Potion',
    image: 'NovaSpark.png',
    recipe: ['star', 'spark'],
    power: 20
  },
  passion: {
    name: 'Passion Pop Potion',
    image: 'PassionPop.png',
    recipe: ['heart', 'spark'],
    power: 40
  },
  soothing: {
    name: 'Soothing Love Potion',
    image: 'SoothingLove.png',
    recipe: ['heart', 'dew'],
    power: 40
  },
  starlove: {
    name: 'Starlove Charm Potion',
    image: 'StarLoveCharm.png',
    recipe: ['heart', 'star'],
    power: 40
  }
};

const SOULS = {
  dream: {
    name: 'Dream Mist Soul',
    image: 'DreamMistSoul.png',
    hpValues: [20, 40, 60, 80]
  },
  energy: {
    name: 'Energy Splash Soul',
    image: 'EnergySplashSoul.png',
    hpValues: [20, 40, 60, 80, 100]
  },
  nova: {
    name: 'Nova Spark Soul',
    image: 'NovaSparkSoul.png',
    hpValues: [20, 40, 60, 80, 100, 120]
  },
  passion: {
    name: 'Passion Pop Soul',
    image: 'PassionPopSoul.png',
    hpValues: [40, 80, 120, 160]
  },
  soothing: {
    name: 'Soothing Love Soul',
    image: 'SoothingLoveSoul.png',
    hpValues: [40, 80, 120, 160, 200]
  },
  starlove: {
    name: 'Starlove Charm Soul',
    image: 'StarLoveCharmSoul.png',
    hpValues: [40, 80, 120, 160, 200, 240]
  }
};

const RECIPE_LOOKUP = Object.entries(POTIONS).reduce((lookup, [key, potion]) => {
  lookup[recipeId(potion.recipe)] = key;
  return lookup;
}, {});

const MERGE_SLOT_X = [0, 216];
const SOUL_SLOT_X = [0, 225];
const STATION_SLOT_Y = [0, 172, 359, 530];

const state = {
  currentLevel: 1,
  timeLeft: 0,
  required: 0,
  defeated: 0,
  soulBelt: [],
  selectedElements: [],
  potions: Array(8).fill(null),
  nextPotionSlot: 0,
  selectedPotionSlot: null,
  potionSerial: 1,
  gameOver: false
};

let timerId = null;
let toastId = null;

showMainMenu();

function asset(name) {
  return `${ASSET_DIR}${name}`;
}

function percent(value, total) {
  return `${(value / total) * 100}%`;
}

function setBounds(element, x, y, width, height) {
  element.style.left = percent(x, BASE_WIDTH);
  element.style.top = percent(y, BASE_HEIGHT);
  element.style.width = percent(width, BASE_WIDTH);
  element.style.height = percent(height, BASE_HEIGHT);
}

function scene(imageName) {
  stopTimer();
  stage.innerHTML = '';
  const image = document.createElement('img');
  image.className = 'scene-background';
  image.src = asset(imageName);
  image.alt = '';
  stage.append(image);
}

function hotspot(label, x, y, width, height, handler) {
  const button = document.createElement('button');
  button.type = 'button';
  button.className = 'hotspot';
  button.setAttribute('aria-label', label);
  button.textContent = label;
  setBounds(button, x, y, width, height);
  button.addEventListener('click', handler);
  stage.append(button);
  return button;
}

function showMainMenu() {
  scene('HomeScreenPic.png');
  hotspot('Play', 794, 388, 338, 123, showLevelSelection);
  hotspot('How to play', 749, 550, 423, 123, showHowToPlay);
  hotspot('Credits', 847, 725, 253, 123, showCredits);
}

function showLevelSelection() {
  scene('LevelSelectionScreenPic.png');
  hotspot('Level 1', 429, 232, 1103, 123, () => startGame(1));
  hotspot('Level 2', 523, 375, 900, 123, () => startGame(2));
  hotspot('Level 3', 546, 528, 874, 123, () => startGame(3));
  hotspot('Level 4', 462, 677, 1037, 123, () => startGame(4));
  hotspot('Level 5', 462, 821, 1037, 123, () => startGame(5));
  hotspot('Back', 64, 64, 203, 98, showMainMenu);
}

function showHowToPlay() {
  scene('HowToPlayPic.png');
  hotspot('Back', 64, 64, 203, 98, showMainMenu);
}

function showCredits() {
  scene('CreditScreenPic.png');
  hotspot('Back', 64, 64, 203, 98, showMainMenu);
}

function startGame(level) {
  stopTimer();
  const rules = LEVEL_RULES[level] || LEVEL_RULES[1];
  state.currentLevel = level;
  state.timeLeft = rules.time;
  state.required = rules.required;
  state.defeated = 0;
  state.soulBelt = [];
  state.selectedElements = [];
  state.potions = Array(8).fill(null);
  state.nextPotionSlot = 0;
  state.selectedPotionSlot = null;
  state.potionSerial = 1;
  state.gameOver = false;
  fillSoulBelt();
  renderGame();
  timerId = window.setInterval(tickTimer, 1000);
}

function tickTimer() {
  if (state.gameOver) {
    return;
  }
  state.timeLeft = Math.max(0, state.timeLeft - 1);
  updateStatusLabels();
  if (state.timeLeft === 0) {
    state.gameOver = true;
    stopTimer();
    showResultModal('Game Over', 'Sia Jai Duay Na, Try Again Dai Mai Kub', [
      { label: 'Try Again', action: () => startGame(state.currentLevel) },
      { label: 'Main Menu', action: showMainMenu, secondary: true }
    ]);
  }
}

function renderGame() {
  stage.innerHTML = '';
  const image = document.createElement('img');
  image.className = 'scene-background';
  image.src = asset('GameScreenPic.png');
  image.alt = '';
  stage.append(image);

  hotspot('Back', 27, 25, 96, 104, () => {
    if (window.confirm('Back to Main Menu?')) {
      showMainMenu();
    }
  });

  addLabel('time-label', 1200, 75);
  addLabel('progress-label', 1200, 150);
  addLabel('level-label', 1200, 225);
  updateStatusLabels();

  hotspot('Heart Berry', 247, 874, 232, 127, () => selectElement('heart'));
  hotspot('Star Dust', 499, 874, 232, 127, () => selectElement('star'));
  hotspot('Spark Ember', 753, 874, 232, 127, () => selectElement('spark'));
  hotspot('Dew Drop', 1003, 874, 232, 127, () => selectElement('dew'));

  renderPotionSlots();
  renderSoulSlots();
  renderTrashZone();
}

function addLabel(id, x, y) {
  const label = document.createElement('div');
  label.id = id;
  label.className = 'game-label';
  label.style.left = percent(x, BASE_WIDTH);
  label.style.top = percent(y, BASE_HEIGHT);
  stage.append(label);
}

function updateStatusLabels() {
  const timeLabel = document.getElementById('time-label');
  const progressLabel = document.getElementById('progress-label');
  const levelLabel = document.getElementById('level-label');
  if (timeLabel) {
    timeLabel.textContent = String(state.timeLeft);
  }
  if (progressLabel) {
    progressLabel.textContent = `${state.defeated} / ${state.required}`;
  }
  if (levelLabel) {
    levelLabel.textContent = `${state.currentLevel} / 5`;
  }
}

function selectElement(elementKey) {
  if (state.gameOver) {
    return;
  }
  if (state.selectedElements.length >= 2) {
    state.selectedElements = [];
  }
  state.selectedElements.push(elementKey);

  const potionKey = RECIPE_LOOKUP[recipeId(state.selectedElements)];
  if (potionKey) {
    state.selectedElements = [];
    addPotion(potionKey);
    renderGame();
    showToast(`${POTIONS[potionKey].name} created`);
    return;
  }

  if (state.selectedElements.length === 2) {
    state.selectedElements = [];
    showToast('No potion recipe');
  }
}

function addPotion(potionKey) {
  const emptySlot = state.potions.findIndex((potion) => potion === null);
  let slotIndex = emptySlot;
  if (slotIndex === -1) {
    if (state.nextPotionSlot >= state.potions.length) {
      state.nextPotionSlot = 0;
    }
    slotIndex = state.nextPotionSlot;
    state.nextPotionSlot += 1;
  }

  state.potions[slotIndex] = {
    id: state.potionSerial,
    key: potionKey
  };
  state.potionSerial += 1;
  state.selectedPotionSlot = slotIndex;
}

function renderPotionSlots() {
  let index = 0;
  for (const yOffset of STATION_SLOT_Y) {
    for (const xOffset of MERGE_SLOT_X) {
      const potion = state.potions[index];
      const slot = document.createElement('button');
      slot.type = 'button';
      slot.className = `game-slot potion-slot${potion ? ' has-item' : ''}${state.selectedPotionSlot === index ? ' selected' : ''}`;
      slot.setAttribute('aria-label', potion ? POTIONS[potion.key].name : `Potion slot ${index + 1}`);
      setBounds(slot, 110 + xOffset, 110 + yOffset, 176, 151);
      slot.dataset.slotIndex = String(index);

      if (potion) {
        slot.draggable = true;
        slot.addEventListener('dragstart', (event) => {
          event.dataTransfer.setData('text/plain', String(index));
          event.dataTransfer.effectAllowed = 'move';
        });
        slot.addEventListener('click', () => {
          state.selectedPotionSlot = state.selectedPotionSlot === index ? null : index;
          renderGame();
        });
        slot.append(gameImage(POTIONS[potion.key].image, POTIONS[potion.key].name));
      }

      stage.append(slot);
      index += 1;
    }
  }
}

function renderSoulSlots() {
  let index = 0;
  for (const yOffset of STATION_SLOT_Y) {
    for (const xOffset of SOUL_SLOT_X) {
      const soul = state.soulBelt[index];
      const slot = document.createElement('button');
      slot.type = 'button';
      slot.className = `game-slot soul-slot drop-target${soul ? '' : ' empty'}`;
      slot.setAttribute('aria-label', soul ? `${soul.name}, HP ${soul.hp}` : `Soul slot ${index + 1}`);
      setBounds(slot, 775 + xOffset, 110 + yOffset, 176, 151);
      slot.dataset.soulIndex = String(index);

      if (soul) {
        slot.addEventListener('dragover', (event) => {
          event.preventDefault();
          slot.classList.add('drag-over');
        });
        slot.addEventListener('dragleave', () => {
          slot.classList.remove('drag-over');
        });
        slot.addEventListener('drop', (event) => {
          event.preventDefault();
          slot.classList.remove('drag-over');
          attackSoul(parsePotionSlot(event.dataTransfer.getData('text/plain')), index);
        });
        slot.addEventListener('click', () => {
          if (state.selectedPotionSlot !== null) {
            attackSoul(state.selectedPotionSlot, index);
          }
        });
        slot.append(gameImage(soul.image, soul.name));
        const hp = document.createElement('span');
        hp.className = 'hp-badge';
        hp.textContent = String(soul.hp);
        slot.append(hp);
      }

      stage.append(slot);
      index += 1;
    }
  }
}

function renderTrashZone() {
  const trash = document.createElement('button');
  trash.type = 'button';
  trash.className = 'trash-zone';
  trash.setAttribute('aria-label', 'Discard selected potion');
  setBounds(trash, 561, 444, 176, 151);

  trash.addEventListener('dragover', (event) => {
    event.preventDefault();
    trash.classList.add('drag-over');
  });
  trash.addEventListener('dragleave', () => {
    trash.classList.remove('drag-over');
  });
  trash.addEventListener('drop', (event) => {
    event.preventDefault();
    trash.classList.remove('drag-over');
    discardPotion(parsePotionSlot(event.dataTransfer.getData('text/plain')));
  });
  trash.addEventListener('click', () => {
    if (state.selectedPotionSlot !== null) {
      discardPotion(state.selectedPotionSlot);
    }
  });

  stage.append(trash);
}

function gameImage(imageName, altText) {
  const image = document.createElement('img');
  image.className = 'game-icon';
  image.src = asset(imageName);
  image.alt = altText;
  image.draggable = false;
  return image;
}

function attackSoul(potionSlotIndex, soulIndex) {
  if (state.gameOver) {
    return;
  }
  if (!Number.isInteger(potionSlotIndex) || potionSlotIndex < 0 || potionSlotIndex >= state.potions.length) {
    return;
  }
  const potion = state.potions[potionSlotIndex];
  const soul = state.soulBelt[soulIndex];
  if (!potion || !soul) {
    return;
  }

  let resultMessage = '';
  if (potion.key === soul.type) {
    soul.hp = Math.max(0, soul.hp - POTIONS[potion.key].power);
    if (soul.hp === 0) {
      state.soulBelt.splice(soulIndex, 1);
      state.defeated += 1;
      fillSoulBelt();
      resultMessage = 'Soul defeated';
    } else {
      resultMessage = 'Good hit';
    }
  } else {
    resultMessage = 'Wrong potion';
  }

  state.potions[potionSlotIndex] = null;
  state.selectedPotionSlot = null;

  if (state.defeated >= state.required) {
    state.gameOver = true;
    stopTimer();
    renderGame();
    showLevelCompleteModal();
    return;
  }

  renderGame();
  showToast(resultMessage);
}

function discardPotion(potionSlotIndex) {
  if (!Number.isInteger(potionSlotIndex) || potionSlotIndex < 0 || potionSlotIndex >= state.potions.length) {
    return;
  }
  if (!state.potions[potionSlotIndex]) {
    return;
  }
  state.potions[potionSlotIndex] = null;
  state.selectedPotionSlot = null;
  renderGame();
}

function fillSoulBelt() {
  while (state.soulBelt.length < 8 && state.defeated < state.required) {
    state.soulBelt.push(createSoul());
  }
}

function createSoul() {
  const type = randomItem(unlockedSoulTypes(state.currentLevel));
  const soul = SOULS[type];
  return {
    type,
    name: soul.name,
    image: soul.image,
    hp: randomItem(soul.hpValues)
  };
}

function unlockedSoulTypes(level) {
  const types = ['dream', 'energy', 'nova'];
  if (level >= 2) {
    types.push('passion');
  }
  if (level >= 3) {
    types.push('soothing');
  }
  if (level >= 4) {
    types.push('starlove');
  }
  return types;
}

function randomItem(items) {
  return items[Math.floor(Math.random() * items.length)];
}

function recipeId(elements) {
  return [...elements].sort().join('+');
}

function parsePotionSlot(value) {
  const slotIndex = Number.parseInt(value, 10);
  return Number.isNaN(slotIndex) ? -1 : slotIndex;
}

function showLevelCompleteModal() {
  if (state.currentLevel < 5) {
    showResultModal(`Level ${state.currentLevel} Complete`, `Go to Level ${state.currentLevel + 1}?`, [
      { label: 'Next Level', action: () => startGame(state.currentLevel + 1) },
      { label: 'Main Menu', action: showMainMenu, secondary: true }
    ]);
    return;
  }

  showResultModal('All Levels Complete', 'You cleared all levels!', [
    { label: 'Main Menu', action: showMainMenu }
  ]);
}

function showResultModal(title, text, actions) {
  const backdrop = document.createElement('div');
  backdrop.className = 'modal-backdrop';

  const modal = document.createElement('section');
  modal.className = 'modal';
  modal.setAttribute('role', 'dialog');
  modal.setAttribute('aria-modal', 'true');

  const heading = document.createElement('h1');
  heading.className = 'modal-title';
  heading.textContent = title;

  const message = document.createElement('p');
  message.className = 'modal-text';
  message.textContent = text;

  const actionBar = document.createElement('div');
  actionBar.className = 'modal-actions';

  for (const item of actions) {
    const button = document.createElement('button');
    button.type = 'button';
    button.className = `modal-button${item.secondary ? ' secondary' : ''}`;
    button.textContent = item.label;
    button.addEventListener('click', item.action);
    actionBar.append(button);
  }

  modal.append(heading, message, actionBar);
  backdrop.append(modal);
  stage.append(backdrop);
  const firstButton = actionBar.querySelector('button');
  if (firstButton) {
    firstButton.focus();
  }
}

function showToast(message) {
  const existing = stage.querySelector('.toast');
  if (existing) {
    existing.remove();
  }
  window.clearTimeout(toastId);

  const toast = document.createElement('div');
  toast.className = 'toast';
  toast.textContent = message;
  stage.append(toast);
  toastId = window.setTimeout(() => {
    toast.remove();
  }, 1100);
}

function stopTimer() {
  if (timerId !== null) {
    window.clearInterval(timerId);
    timerId = null;
  }
}
