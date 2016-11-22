package application.tableview;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Scope {
    なし,
    敵単体,
    敵全体,
    敵1体ランダム,
    敵2体ランダム,
    敵3体ランダム,
    敵4体ランダム,
    味方単体,
    味方全体,
    味方単体_戦闘不能,
    味方全体_戦闘不能,
    使用者;

  public static List<String> getNameList() {
    return Arrays.stream(Scope.values())
        .map(s -> s.name())
        .collect(Collectors.toList());
  }
}
