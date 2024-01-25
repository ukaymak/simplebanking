package com.eteration.simplebanking.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> {
  private T response;

  public BaseResponse(T response) {
    this.response = response;
  }
}
