package programmers.Q92341.lv2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, Integer> car_times = new HashMap<>();
        for (String record : records) {
            String[] split = record.split(" ");
            // IN의 경우
            if (split[2].equals("IN")) {
                map.put(split[1], split[0]);
                continue;
            }
            // Out의 경우
            int totalTime = getTotalTime(map.get(split[1]), split[0]);
            car_times.put(split[1], car_times.getOrDefault(split[1], 0) + totalTime);
            map.remove(split[1]);
        }

        // 나가지 않은 차들
        for (String key : map.keySet()) {
            int totalTime = getTotalTime(map.get(key), "23:59");
            car_times.put(key, car_times.getOrDefault(key, 0) + totalTime);
        }

        List<String> cars = new ArrayList<>(car_times.keySet());
        Collections.sort(cars);
        answer = new int[cars.size()];

        for (int i = 0; i < cars.size(); i++) {
            int totalTime = car_times.get(cars.get(i));
            answer[i] = getTotalFee(fees, totalTime);
        }

        return answer;
    }

    // 총 요금 구하기
    private int getTotalFee(int[] fees, int totalTime) {
        int totalFee = fees[1];
        if (totalTime > fees[0]) {
            int addedTime = totalTime - fees[0];
            totalFee += (addedTime / fees[2]) * fees[3];
            if (addedTime % fees[2] != 0) {
                totalFee += fees[3];
            }
        }
        return totalFee;
    }

    // 입차 출차 시간으로 이용시간 구하기
    private int getTotalTime(String sTime, String eTime) {
        String[] sTimes = sTime.split(":");
        String[] eTimes = eTime.split(":");

        int sHour = Integer.parseInt(sTimes[0]);
        int sMinute = Integer.parseInt(sTimes[1]);

        int eHour = Integer.parseInt(eTimes[0]);
        int eMinute = Integer.parseInt(eTimes[1]);
        int totalTime = (eHour - sHour) * 60 + eMinute - sMinute;
        return totalTime;
    }
}