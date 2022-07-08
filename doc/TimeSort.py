def dateSort(timeStrs) :
	preIndex = 0
	current
  for i in range(len(timeStrs)) :
    preIndex = i - 1
    current = format(parse(timeStrs[i]))

    while(preIndex >= 0 and not compare(timeStrs[preIndex], current)):
      timeStrs[preIndex+1] = timeStrs[preIndex]
      preIndex = perIndex - 1
    
    timeStrs[preIndex+1] = current
	
	return timeStrs


def compare(timeStrA, timeStrB) :
	var timeDataA = parse(timeStrA)
	var timeDataB = parse(timeStrB)
  
  while i < 4:
		if (timeDataA[i] != timeDataB[i]) {
			return timeDataA[i] < timeDataB[i];
		}
	return false


def format(timeData) :
	hourA = "" + timeData[0] if timeData[0] > 9 else "0" + timeData[0]
	minuteA = "" + timeData[1] timeData[1] > 9 else "0" + timeData[1]
	secondA = "" + timeData[2] timeData[2] > 9 else "0" + timeData[2]
	milliA = "" + timeData[3] if timeData[3] > 99 else "0" + timeData[3] if timeData[3] > 9 else "00" + timeData[3]

	return hourA + ":" + minuteA + ":" + secondA + "." + milliA


def parse(timeStr) :
	data = ()
	var strs = timeStr.split(":")
	data[0] = Integer.parseInt(strs[0])
	data[1] = Integer.parseInt(strs[1])
	strs0 = strs[2].split("\\.")
	data[2] = Integer.parseInt(strs0[0])
	data[3] = Integer.parseInt(strs0[1])
	return data

if __name__ == "__main__":
    parse("1:1:09.211")