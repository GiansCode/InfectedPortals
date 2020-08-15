/*
 * Copyright (c) 2020 Lewys Davies
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.piggypiglet.infectedportals.utils.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ProbabilityCollection<E> {
    private final NavigableSet<ProbabilitySetElement<E>> collection =
            new TreeSet<>(Comparator.comparingInt(ProbabilitySetElement::getIndex));
    private final SplittableRandom random = new SplittableRandom();

    private int totalProbability = 0;

    public void add(@NotNull final E object, final int probability) {
        if (probability <= 0) {
            throw new IllegalArgumentException("Probability must be greater than 0");
        }

        final ProbabilitySetElement<E> entry = new ProbabilitySetElement<>(object, probability);
        entry.setIndex(totalProbability + 1);

        collection.add(entry);
        totalProbability += probability;
    }

    @NotNull
    public E get() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get an object out of a empty collection");
        }

        final ProbabilitySetElement<E> toFind = new ProbabilitySetElement<>(null, 0);
        toFind.setIndex(random.nextInt(1, totalProbability + 1));

        return Objects.requireNonNull(Objects.requireNonNull(collection.floor(toFind)).getObject());
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    private static final class ProbabilitySetElement<T> {
        private final T object;
        private final int probability;
        private int index;

        protected ProbabilitySetElement(@Nullable final T object, final int probability) {
            this.object = object;
            this.probability = probability;
        }

        @Nullable
        public T getObject() {
            return object;
        }

        public int getProbability() {
            return probability;
        }

        private int getIndex() {
            return index;
        }

        private void setIndex(final int index) {
            this.index = index;
        }
    }
}
